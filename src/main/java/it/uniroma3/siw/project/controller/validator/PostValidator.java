package it.uniroma3.siw.project.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.project.model.Post;

@Component
public class PostValidator implements Validator{

    @Override
    public boolean supports(Class<?> clazz) {
        return Post.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Post post = (Post) target;
        if(post.getText() == null){
            errors.reject("post.empty");
        }
    }
    
}
