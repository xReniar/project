package it.uniroma3.siw.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.project.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    public boolean existsByEmail(String email);

    /* CUSTOM QUERY - trova tutti gli user i cui username contengono la sottostringa */
    @Query("SELECT u from users u where u.username LIKE '%:substring%'")
    List<User> findByUsernameContainingSubstring(@Param("substring") String substring);

}