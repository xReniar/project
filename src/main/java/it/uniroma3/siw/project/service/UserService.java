package it.uniroma3.siw.project.service;

import it.uniroma3.siw.project.authentication.AuthenticationProvider;
import it.uniroma3.siw.project.model.Image;
import it.uniroma3.siw.project.model.User;
import it.uniroma3.siw.project.repository.ImageRepository;
import it.uniroma3.siw.project.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    protected UserRepository userRepository;

    @Transactional
    public User getUser(Long id) {
        Optional<User> result = this.userRepository.findById(id);
        return result.orElse(null);
    }

    @Transactional
    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    @Transactional
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        Iterable<User> iterable = this.userRepository.findAll();
        for(User user : iterable)
            result.add(user);
        return result;
    }

    public User getUserByEmail(String email){
        return this.userRepository.findByEmail(email);
    }

    
    public void createNewUserAfterOAuthLoginSuccess(String email,String name,String surname,String username,AuthenticationProvider provider) throws IOException {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setSurname(surname);
        user.setUsername(username);
        user.setAuthProvider(provider);

        Image profilePicture = new Image(this.resourceLoader.getResource("classpath:static/images/noProfile.jpeg").getContentAsByteArray());
        this.imageRepository.save(profilePicture);
        user.setProfilePicture(profilePicture);
        this.userRepository.save(user);
    }

    public void updateUserAfterOAuthLoginSuccess(User user, String name, String surname,AuthenticationProvider provider) {
        user.setName(name);
        user.setSurname(surname);
        if(user.getAuthProvider() != null){
            user.setAuthProvider(provider);
        }

        this.userRepository.save(user);
    }

}
