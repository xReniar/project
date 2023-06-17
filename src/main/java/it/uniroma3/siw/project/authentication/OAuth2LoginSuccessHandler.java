package it.uniroma3.siw.project.authentication;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.project.model.User;
import it.uniroma3.siw.project.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getEmail();
        User user = this.userService.getUserByEmail(email);

        String[] fullName = oAuth2User.getName().split(" ");
        String name = String.join(" ", Arrays.copyOfRange(fullName, 0,fullName.length - 1));
        String surname = fullName[fullName.length - 1];
        String[] fullUsername = email.split("@");
        String username = fullUsername[0];

        if(user == null) {
            userService.createNewUserAfterOAuthLoginSuccess(email,name,surname,username,AuthenticationProvider.GOOGLE);
        } else {
            //userService.updateUserAfterOAuthLoginSuccess(user,name,surname,AuthenticationProvider.LOCAL);  
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
