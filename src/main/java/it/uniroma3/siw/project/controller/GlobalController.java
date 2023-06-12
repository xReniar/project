package it.uniroma3.siw.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import it.uniroma3.siw.project.model.Credentials;
import it.uniroma3.siw.project.model.User;
import it.uniroma3.siw.project.repository.CredentialsRepository;

@ControllerAdvice
public class GlobalController {
    @Autowired
    CredentialsRepository credentialsRepository;

    @ModelAttribute("userDetails")
    public UserDetails getUser() {
        UserDetails user = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            user = (UserDetails) authentication.getPrincipal();
        }
        return user;
    }

    @ModelAttribute("currentUser")
    public User getCurrentUser(){
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Credentials credential = this.credentialsRepository.findByUsername(username).get();
            return credential.getUser();
        } catch(Exception e) {
            return null;
        }
    }

    /*
     * @ModelAttribute("user_NameSurname")
    public String getUser_NameSurname(){
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = this.credentialsRepository.findByUsername(username).get().getUser();
            return user.getName() + " " + user.getSurname();
        } catch(Exception e){
            return "";
        }
    }
     */
}
