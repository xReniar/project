package it.uniroma3.siw.project.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.project.model.Comment;
public interface CommentRepository extends CrudRepository<Comment,Long> {

}
