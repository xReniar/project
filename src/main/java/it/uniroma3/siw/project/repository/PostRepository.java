package it.uniroma3.siw.project.repository;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.project.model.Post;

public interface PostRepository extends CrudRepository<Post,Long> {
    public boolean existsByTitleAndContent(String title,String content);
}
