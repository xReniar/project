package it.uniroma3.siw.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.project.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    public boolean existsByEmail(String email);

    public User findByEmail(String email);

    public User findByNameAndSurname(String name,String surname);

    /* CUSTOM QUERY - trova tutti gli user i cui username contengono la sottostringa */
    @Query(value = "SELECT * FROM users WHERE UPPER(username) LIKE UPPER(CONCAT('%',:substring,'%'))", nativeQuery = true)
    List<User> findByUsernameContainingSubstring(String substring);

}