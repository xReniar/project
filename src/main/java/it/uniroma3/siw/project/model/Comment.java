package it.uniroma3.siw.project.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String text;
    @NotBlank
    @ManyToOne
    private User author;
    @NotBlank
    @OneToMany
    private Set<User> likedUsers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Set<User> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(Set<User> likedUsers) {
        this.likedUsers = likedUsers;
    }
}
