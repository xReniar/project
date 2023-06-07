package it.uniroma3.siw.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.project.model.Comment;
import it.uniroma3.siw.project.model.Post;
import it.uniroma3.siw.project.repository.CommentRepository;
import it.uniroma3.siw.project.repository.PostRepository;
import jakarta.validation.Valid;

@Controller
public class CommentController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    GlobalController globalController;

    @PostMapping("/user/comment/{postId}")
    public String newPostComment(Model model,@Valid @ModelAttribute("comment") Comment comment,BindingResult bindingResult,
                                 @PathVariable("postId") Long postId){
        Post post = this.postRepository.findById(postId).get();
        comment.setAuthor(this.globalController.getCurrentUser());
        if(!this.postRepository.hasCommentWithAuthor(post,this.globalController.getCurrentUser())){
            post.getComments().add(comment);
            this.commentRepository.save(comment);
            this.postRepository.save(post);
        }
        return "";
    }
}
