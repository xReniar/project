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

import it.uniroma3.siw.project.service.CustomOAuth2UserService;

import static it.uniroma3.siw.project.model.Credentials.LOGGED_ROLE;
import static it.uniroma3.siw.project.model.Credentials.OAUTH2_ROLE;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {

    @Autowired
    DataSource datasource;

    @Autowired
    CustomOAuth2UserService oAuth2UserService;
    
    @Autowired
    OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

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
                                        .requestMatchers(HttpMethod.GET,"/oauth2/**").permitAll()
                                        .requestMatchers(HttpMethod.POST,"/oauth2/**").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/login", "/register","/","/image/{id}",
                                                                         "/css/**", "/images/**", "favicon.ico").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/login", "/register").permitAll()
                                        .requestMatchers(HttpMethod.GET,"/user/**").hasAnyAuthority(LOGGED_ROLE,OAUTH2_ROLE)
                                        .requestMatchers(HttpMethod.POST,"/user/**").hasAnyAuthority(LOGGED_ROLE,OAUTH2_ROLE)
                                        .anyRequest().authenticated()
                                                .and().exceptionHandling().accessDeniedPage("/error");
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                        // solo gli utenti autenticati con ruolo ADMIN possono accedere a risorse con path /user/**

                )
                .formLogin((form) -> {
                    try {
                        form
                                .loginPage("/login")
                                .defaultSuccessUrl("/success")
                                .permitAll()
                                .and()
                                .oauth2Login()
                                    .loginPage("/login")
                                    .userInfoEndpoint()
                                    .userService(oAuth2UserService)
                                    .and()
                                    .successHandler(oAuth2LoginSuccessHandler);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
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