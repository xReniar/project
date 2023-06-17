package it.uniroma3.siw.project.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import it.uniroma3.siw.project.authentication.CustomOAuth2User;
import it.uniroma3.siw.project.model.Credentials;
import it.uniroma3.siw.project.model.User;
import it.uniroma3.siw.project.repository.CredentialsRepository;
import it.uniroma3.siw.project.repository.UserRepository;
import it.uniroma3.siw.project.authentication.AuthenticationProvider;

@ControllerAdvice
public class GlobalController {
    @Autowired
    CredentialsRepository credentialsRepository;

    @Autowired
    UserRepository userRepository;

    @ModelAttribute("currentUser")
    public User getCurrentUser(){
        try {
            if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass().equals(String.class)){
                return null;
            }
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass().equals(CustomOAuth2User.class)){
                String[] fullName = username.split(" ");
                String name = String.join(" ", Arrays.copyOfRange(fullName, 0,fullName.length - 1));
                String surname = fullName[fullName.length - 1];
                return this.userRepository.findByNameAndSurnameAndAuthProvider(name, surname,AuthenticationProvider.GOOGLE);
            }
            if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass().equals(org.springframework.security.core.userdetails.User.class)){
                Credentials credential = this.credentialsRepository.findByUsername(username).get();
                return credential.getUser();
            }
            return null;
        } catch(Exception e) {
            return null;
        }
    }
}
