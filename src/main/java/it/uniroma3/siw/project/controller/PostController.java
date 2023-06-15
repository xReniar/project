package it.uniroma3.siw.project.controller;

import it.uniroma3.siw.project.controller.validator.PostValidator;
import it.uniroma3.siw.project.model.Comment;
import it.uniroma3.siw.project.model.Image;
import it.uniroma3.siw.project.model.Post;
import it.uniroma3.siw.project.model.User;
import it.uniroma3.siw.project.repository.ImageRepository;
import it.uniroma3.siw.project.repository.PostRepository;
import it.uniroma3.siw.project.repository.UserRepository;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    PostValidator postValidator;

    @GetMapping("/user/formNewPost")
    public String newPost(Model model){
        model.addAttribute("post",new Post());
        return "formNewPost.html";
    }

    @PostMapping("/user/uploadPost")
    public String uploadPost(Model model,@Valid @ModelAttribute("post") Post post,BindingResult bindingResult,
                             @RequestParam("file") MultipartFile image) throws IOException {
        
        this.postValidator.validate(post,bindingResult);
        if(!bindingResult.hasErrors()){
            // setting up the images of the post
            Image img = new Image(image.getBytes());
            this.imageRepository.save(img);
            post.setPicture(img);

            User author = this.globalController.getCurrentUser();
            // setting up the author of the post and adding the post to the list of user's posts       
            post.setAuthor(author);
            author.getPosts().add(post);

            this.userRepository.save(author);
            Post savedPost = this.postRepository.save(post);
            User currentUser = this.globalController.getCurrentUser();
            model.addAttribute("numPosts", currentUser.getPosts().size());
            model.addAttribute("numFollowers", currentUser.getUsersFollowers().size());
            model.addAttribute("numFollowing", currentUser.getUsersFollowing().size());
            model.addAttribute("posts", this.postRepository.findAll());
            Post correctPost = this.postRepository.findById(savedPost.getId()).get();
            correctPost.setLikedUsers(new HashSet<User>());
            model.addAttribute("post", correctPost);
            model.addAttribute("comment", new Comment());
            model.addAttribute("comments", correctPost.getComments());
            return "post.html";
        }
        return "formNewPost.html";
    }

    @GetMapping("/user/post/{postId}")
    public String getPost(Model model,@PathVariable("postId") Long id){
        Post post = this.postRepository.findById(id).get();
        model.addAttribute("post", post);
        if(!this.postRepository.hasCommentWithAuthor(post, this.globalController.getCurrentUser()))
            model.addAttribute("comment", new Comment());
        model.addAttribute("comments", post.getComments());
        return "post.html";
    }

    @GetMapping("/user/post/like/{postId}")
    public String likePost(Model model,@PathVariable("postId") Long id){
        Post post = this.postRepository.findById(id).get();
        if(!this.globalController.getCurrentUser().getLikedPosts().contains(post)){
            post.getLikedUsers().add(this.globalController.getCurrentUser());
            this.globalController.getCurrentUser().getLikedPosts().add(post);
            this.userRepository.save(this.globalController.getCurrentUser());
            this.postRepository.save(post);
        }
        model.addAttribute("post", post);
        if(!this.postRepository.hasCommentWithAuthor(post, this.globalController.getCurrentUser()))
            model.addAttribute("comment", new Comment());
        model.addAttribute("comments", post.getComments());
        return "post.html";
    }

    @GetMapping("/user/post/unlike/{postId}")
    public String unlikePost(Model model,@PathVariable("postId") Long id){
        Post post = this.postRepository.findById(id).get();
        if(this.globalController.getCurrentUser().getLikedPosts().contains(post)){
            post.getLikedUsers().remove(this.globalController.getCurrentUser());
            this.globalController.getCurrentUser().getLikedPosts().remove(post);
            this.userRepository.save(this.globalController.getCurrentUser());
            this.postRepository.save(post);
        }
        model.addAttribute("post", post);
        if(!this.postRepository.hasCommentWithAuthor(post, this.globalController.getCurrentUser()))
            model.addAttribute("comment", new Comment());
        model.addAttribute("comments", post.getComments());
        return "post.html";
    }
}
