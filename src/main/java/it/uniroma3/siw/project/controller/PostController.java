package it.uniroma3.siw.project.controller;

import it.uniroma3.siw.project.model.Image;
import it.uniroma3.siw.project.model.Post;
import it.uniroma3.siw.project.model.User;
import it.uniroma3.siw.project.repository.ImageRepository;
import it.uniroma3.siw.project.repository.PostRepository;
import it.uniroma3.siw.project.repository.UserRepository;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PostController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    GlobalController globalController;

    @GetMapping("/user/formNewPost")
    public String newPost(Model model){
        model.addAttribute("post",new Post());
        return "uploadImageForm.html";
    }

    @PostMapping("/user/uploadPost")
    public String uploadPost(Model model,@Valid @ModelAttribute("post") Post post,BindingResult bindingResult,
                             @RequestParam("files") MultipartFile[] images) throws IOException{
        
        if(!bindingResult.hasErrors()){

            // setting up the images of the post
            Set<Image> postImgs = new TreeSet<>();
            for(MultipartFile image : images){
                Image img = new Image(image.getBytes());
                this.imageRepository.save(img);
                postImgs.add(img);
            }
            post.setPictures(postImgs);

            User author = this.globalController.getAuthor();
            // setting up the author of the post and adding the post to the list of user's posts       
            post.setAuthor(author);
            author.getPosts().add(post);

            this.userRepository.save(author);
            this.postRepository.save(post);
        }
        return "post.html";
    }
}
