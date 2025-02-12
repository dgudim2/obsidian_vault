package lt.vgtu.hw1.repos;

import lt.vgtu.hw1.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
}
