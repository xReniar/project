package it.uniroma3.siw.project.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private String username;

    @OneToMany(mappedBy = "author")
    private List<Post> userPost;

}
