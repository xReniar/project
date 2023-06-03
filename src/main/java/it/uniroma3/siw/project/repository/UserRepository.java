package it.uniroma3.siw.project.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.project.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    
}