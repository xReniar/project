package it.uniroma3.siw.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.project.repository.UserRepository;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/user/settings")
    public String settings(Model model){
        return "settings.html";
    }
    
}
