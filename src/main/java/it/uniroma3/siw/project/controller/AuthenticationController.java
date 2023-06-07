package it.uniroma3.siw.project.controller;

import it.uniroma3.siw.project.controller.validator.CredentialsValidator;
import it.uniroma3.siw.project.controller.validator.UserValidator;
import it.uniroma3.siw.project.model.Credentials;
import it.uniroma3.siw.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

import it.uniroma3.siw.project.service.CredentialsService;

@Controller
public class AuthenticationController {

    @Autowired
    CredentialsService credentialsService;

    @Autowired
    UserValidator userValidator;

    @Autowired
    CredentialsValidator credentialsValidator;

    @Autowired
    GlobalController globalController;

    @GetMapping(value = "/")
    public String welcomePage(Model model) {
        return "welcomePage.html";
    }

    @GetMapping(value = "/register")
    public String showRegisterForm (Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("credentials", new Credentials());
        return "registerPage.html";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult userBindingResult, @Valid
                               @ModelAttribute("credentials") Credentials credentials,
                               BindingResult credentialsBindingResult,
                               Model model) {
        this.userValidator.validate(user, userBindingResult);
        this.credentialsValidator.validate(credentials, credentialsBindingResult);
        // se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
        if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
            credentials.setUser(user);
            credentialsService.saveCredentials(credentials);
            model.addAttribute("user", user);
            return "loginPage.html";
        }
        return "registerPage.html";
    }

    @GetMapping(value = "/login")
    public String showLoginForm (Model model) {
        return "loginPage.html";
    }

    @GetMapping(value = {"/success","/index"})
    public String defaultAfterLogin(Model model) {
        return "index.html";
    }
}
