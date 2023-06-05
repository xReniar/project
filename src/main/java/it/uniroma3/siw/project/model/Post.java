package it.uniroma3.siw.project.model;

import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @ManyToOne
    private User author;
    @NotBlank
    private String text;

    @OneToMany
    private Set<Image> pictures;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<User> likedUsers;
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Comment> comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<User> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(Set<User> likedUsers) {
        this.likedUsers = likedUsers;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Image> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Image> pictures) {
        this.pictures = pictures;
    }
}
