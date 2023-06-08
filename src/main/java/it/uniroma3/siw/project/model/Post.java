package it.uniroma3.siw.project.model;

import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;
import java.util.Set;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User author;
    @NotBlank
    private String text;

    @OneToOne
    private Image picture;

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

    public Image getPicture() {
        return picture;
    }

    public void setPicture(Image picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(author, post.author) && Objects.equals(text, post.text) && Objects.equals(picture, post.picture) && Objects.equals(likedUsers, post.likedUsers) && Objects.equals(comments, post.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, text, picture, likedUsers, comments);
    }
}
