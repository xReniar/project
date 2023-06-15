package it.uniroma3.siw.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

        this.imageRepository.save(newProfilePic);
        // setting new profile pic
        currentUser.setProfilePicture(newProfilePic);
        // deleting old pic and and saving new pic, updating current user too
        this.imageRepository.deleteById(oldProfilePic.getId());
        this.userRepository.save(currentUser);
        User user = this.globalController.getCurrentUser();
        model.addAttribute("posts",user.getPosts());
        model.addAttribute("followers",user.getUsersFollowers());
        model.addAttribute("following",user.getUsersFollowing());
        return "personalAccount.html";
    }

    @GetMapping("/user/{userId}")
    public String getUserProfile(Model model,@PathVariable("userId") Long userId) {
        User otherUser = this.userRepository.findById(userId).get();
        model.addAttribute("user", otherUser);
        model.addAttribute("posts", otherUser.getPosts());
        model.addAttribute("followers", otherUser.getUsersFollowers());
        model.addAttribute("following", otherUser.getUsersFollowing());
        if(userId == this.globalController.getCurrentUser().getId()){
            return "personalAccount.html";
        }
        return "userAccount.html";
    }

    @GetMapping("/user/followUser/{userId}")
    public String followUser(Model model, @PathVariable("userId") Long userId){

        User currentUser = this.globalController.getCurrentUser();
        User userToFollow = this.userRepository.findById(userId).get();

        currentUser.getUsersFollowing().add(userToFollow);
        this.userRepository.save(currentUser);

        userToFollow.getUsersFollowers().add(currentUser);
        this.userRepository.save(userToFollow);

        model.addAttribute("user", userToFollow);
        model.addAttribute("posts",userToFollow.getPosts());
        model.addAttribute("followers",userToFollow.getUsersFollowers());
        model.addAttribute("following",userToFollow.getUsersFollowing());
        return "userAccount.html";
    }

    @GetMapping("/user/unfollowUser/{userId}")
    public String unfollowUser(Model model, @PathVariable("userId") Long userId){

        User currentUser = this.globalController.getCurrentUser();
        User userToUnfollow = this.userRepository.findById(userId).get();

        currentUser.getUsersFollowing().remove(userToUnfollow);
        this.userRepository.save(currentUser);

        userToUnfollow.getUsersFollowers().remove(currentUser);
        this.userRepository.save(userToUnfollow);

        model.addAttribute("user", userToUnfollow);
        model.addAttribute("posts",userToUnfollow.getPosts());
        model.addAttribute("followers",userToUnfollow.getUsersFollowers());
        model.addAttribute("following",userToUnfollow.getUsersFollowing());
        return "userAccount.html";
    }    
}
