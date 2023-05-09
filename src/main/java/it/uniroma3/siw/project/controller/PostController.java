package it.uniroma3.siw.project.controller;

import it.uniroma3.siw.project.model.Post;
import it.uniroma3.siw.project.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

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
    @GetMapping("/uploadImage")
    public String getUploadImageForm(Model model){
        model.addAttribute("post",new Post());
        return "uploadImageForm.html";
    }

}
