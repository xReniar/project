package it.uniroma3.siw.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import it.uniroma3.siw.project.authentication.AuthenticationProvider;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private String email;

    private String username;

    @OneToOne
    private Image profilePicture;

    @Enumerated(EnumType.STRING)
    private AuthenticationProvider authProvider;

    @OneToMany(mappedBy = "author",fetch = FetchType.LAZY)
    @OrderBy("id desc")
    private List<Post> posts;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<User> usersFollowing;

    @ManyToMany(mappedBy = "usersFollowing",fetch = FetchType.LAZY)
    private Set<User> usersFollowers;

    @ManyToMany(mappedBy = "likedUsers",fetch = FetchType.LAZY)
    private Set<Post> likedPosts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Set<User> getUsersFollowing() {
        return usersFollowing;
    }

    public void setUsersFollowing(Set<User> usersFollowing) {
        this.usersFollowing = usersFollowing;
    }

    public Set<User> getUsersFollowers() {
        return usersFollowers;
    }

    public void setUsersFollowers(Set<User> usersFollowers) {
        this.usersFollowers = usersFollowers;
    }

    public Set<Post> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(Set<Post> likedPosts) {
        this.likedPosts = likedPosts;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public Image getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Image profilePicture) {
        this.profilePicture = profilePicture;
    }

    public AuthenticationProvider getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(AuthenticationProvider authProvider) {
        this.authProvider = authProvider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(posts, user.posts) && Objects.equals(usersFollowing, user.usersFollowing) && Objects.equals(usersFollowers, user.usersFollowers) && Objects.equals(likedPosts,user.likedPosts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname);
    }
}
