package it.uniroma3.siw.project.authentication;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static it.uniroma3.siw.project.model.Credentials.LOGGED_ROLE;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {

    @Autowired
    DataSource datasource;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> {
                            try {
                                requests
                                        .requestMatchers(HttpMethod.GET, "/login", "/register","/","/image/{id}",
                                                                         "/css/**", "/images/**", "favicon.ico").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/login", "/register").permitAll()
                                        .requestMatchers(HttpMethod.GET,"/user/**").hasAnyAuthority(LOGGED_ROLE)
                                        .requestMatchers(HttpMethod.POST,"/user/**").hasAnyAuthority(LOGGED_ROLE)
                                        .anyRequest().authenticated()
                                                .and().exceptionHandling().accessDeniedPage("/error");
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                        // solo gli utenti autenticati con ruolo ADMIN possono accedere a risorse con path /user/**

                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/success")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .clearAuthentication(true).permitAll());
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(datasource)
                .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?")
                .authoritiesByUsernameQuery("SELECT username, role FROM credentials WHERE username=?");
    }
}