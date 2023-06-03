package it.uniroma3.siw.project.controller;

import it.uniroma3.siw.project.model.Post;
import it.uniroma3.siw.project.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {
    @Autowired
    PostRepository postRepository;

   /* @GetMapping(value={"/","index.html"})
    public String index(){
        return "index.html";
    } */

    /*@GetMapping("/personalAccount")
    public String personalAccountForm(){
        return "personalAccount.html";
    }
    */
    @GetMapping("/formNewPost")
    public String newPost(Model model){
        model.addAttribute("post",new Post());
        return "uploadImageForm.html";
    }

    @PostMapping("/uploadPost")
    public String uploadPost(Model model){
        return "";
    }

}
