package it.uniroma3.siw.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private String username;

    @OneToMany(mappedBy = "author")
    private List<Post> posts;

    @OneToMany
    private List<User> usersFollowing;

    @OneToMany
    private List<User> usersFollowers;

}
