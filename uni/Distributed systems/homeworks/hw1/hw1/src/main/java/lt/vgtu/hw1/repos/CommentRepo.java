package lt.vgtu.hw1.repos;

import lt.vgtu.hw1.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Comment repo.
 */
public interface CommentRepo extends JpaRepository<Comment, Integer> {
}
