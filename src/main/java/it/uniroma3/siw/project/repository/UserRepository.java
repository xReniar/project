package it.uniroma3.siw.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.project.authentication.AuthenticationProvider;
import it.uniroma3.siw.project.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    public boolean existsByEmail(String email);

    public User findByEmail(String email);

    public User findByNameAndSurnameAndAuthProvider(String name,String surname,AuthenticationProvider authenticationProvider);

    /* CUSTOM QUERY - trova tutti gli user i cui username contengono la sottostringa */
    @Query(value = "SELECT * FROM users WHERE UPPER(username) LIKE UPPER(CONCAT('%',:substring,'%'))", nativeQuery = true)
    List<User> findByUsernameContainingSubstring(String substring);

    @Query("SELECT uf FROM User u JOIN u.usersFollowing uf WHERE u = :currentUser ORDER BY SIZE(uf.usersFollowers) DESC")
    List<User> findUserWithMostFollowers(@Param("currentUser") User currentUser);

}