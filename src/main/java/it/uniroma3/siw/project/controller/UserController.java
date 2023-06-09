package it.uniroma3.siw.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.project.model.Image;
import it.uniroma3.siw.project.model.User;
import it.uniroma3.siw.project.repository.ImageRepository;
import it.uniroma3.siw.project.repository.UserRepository;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    GlobalController globalController;

    @GetMapping("/user/settings")
    public String settings(Model model){
        User user = this.globalController.getCurrentUser();
        model.addAttribute("posts",user.getPosts());
        model.addAttribute("followers",user.getUsersFollowers());
        model.addAttribute("following",user.getUsersFollowing());

        return "personalAccount.html";
    }

    @PostMapping("/user/updateProfilePicture")
    public String updateProfilePicture(Model model,@RequestParam("image") MultipartFile profilePicture) throws Exception {
        // need to have the current user and save the new and old profile pic
        User currentUser = this.globalController.getCurrentUser();
        Image newProfilePic = new Image(profilePicture.getBytes());
        Image oldProfilePic = currentUser.getProfilePicture();

        // setting new profile pic
        currentUser.setProfilePicture(newProfilePic);
        // deleting old pic and and saving new pic, updating current user too
        this.imageRepository.deleteById(oldProfilePic.getId());
        this.imageRepository.save(newProfilePic);
        this.userRepository.save(currentUser);
        
        return "personalAccount.html";
    }
    
}
