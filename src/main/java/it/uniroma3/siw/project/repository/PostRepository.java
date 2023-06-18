package it.uniroma3.siw.project.repository;

import it.uniroma3.siw.project.model.Comment;
import it.uniroma3.siw.project.model.User;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.project.model.Post;

public interface PostRepository extends CrudRepository<Post,Long> {
    /*public boolean existsByTitleAndContent(String title,String content);*/
    //public List<Post> findAllByOrderByText();
    default boolean hasCommentWithAuthor(Post post, User author){
        for(Comment comment : post.getComments()){
            if(comment.getAuthor().equals(author)){
                return true;
            }
        }
        return false;
    }

    public List<Post> findAllByAuthorInOrderByIdDesc(Set<User> following);

    @Query("SELECT p FROM Post p WHERE p.author <> :currentUser")
    List<Post> findAllPostsExceptCurrentUser(@Param("currentUser") User currentUser);
}
