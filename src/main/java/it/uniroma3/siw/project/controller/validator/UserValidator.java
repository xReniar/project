package it.uniroma3.siw.project.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.project.model.User;
import it.uniroma3.siw.project.repository.UserRepository;

@Component
public class UserValidator implements Validator{
    @Autowired
    UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if(user.getEmail() != null && this.userRepository.existsByEmail(user.getEmail())){
            errors.reject("user.duplicate");
        }
    }
    
}
