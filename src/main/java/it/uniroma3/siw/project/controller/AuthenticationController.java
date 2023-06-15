package it.uniroma3.siw.project.controller;

import it.uniroma3.siw.project.controller.validator.CredentialsValidator;
import it.uniroma3.siw.project.controller.validator.UserValidator;
import it.uniroma3.siw.project.model.Credentials;
import it.uniroma3.siw.project.model.Image;
import it.uniroma3.siw.project.model.Post;
import it.uniroma3.siw.project.model.User;
import it.uniroma3.siw.project.repository.ImageRepository;
import it.uniroma3.siw.project.repository.PostRepository;
import it.uniroma3.siw.project.repository.UserRepository;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
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

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

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
                               BindingResult userBindingResult,
                               @Valid @ModelAttribute("credentials") Credentials credentials,
                               BindingResult credentialsBindingResult,
                               Model model) throws IOException {
        this.userValidator.validate(user, userBindingResult);
        this.credentialsValidator.validate(credentials, credentialsBindingResult);
        // se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
        if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
            Image profilePicture = new Image(this.resourceLoader.getResource("classpath:static/images/noProfile.jpeg").getContentAsByteArray());
            this.imageRepository.save(profilePicture);
            user.setProfilePicture(profilePicture);
            user.setUsername(credentials.getUsername());
            credentials.setUser(user);
            credentialsService.saveCredentials(credentials);
            return "loginPage.html";
        }
        return showRegisterForm(model);
    }

    @GetMapping(value = "/login")
    public String showLoginForm (Model model) {
        return "loginPage.html";
    }

    @GetMapping(value = {"/success","/index"})
    public String defaultAfterLogin(Model model) {
        User currentUser = this.globalController.getCurrentUser();
        model.addAttribute("numPosts", currentUser.getPosts().size());
        model.addAttribute("numFollowers", currentUser.getUsersFollowers().size());
        model.addAttribute("numFollowing", currentUser.getUsersFollowing().size());
        model.addAttribute("posts", this.postRepository.findAllByAuthorIn(this.globalController.getCurrentUser().getUsersFollowing()));
        return "index.html";
    }
}
