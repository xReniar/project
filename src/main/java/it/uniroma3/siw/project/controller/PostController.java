package it.uniroma3.siw.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostController {

    @GetMapping(value={"/","index.html"})
    public String index(){
        return "index.html";
    }

    @GetMapping("/personalAccount")
    public String personalAccountForm(){
        return "personalAccount.html";
    }
}
