package lt.vgtu.hw1.repos;

import lt.vgtu.hw1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface User repo.
 */
public interface UserRepo extends JpaRepository<User, Integer> {

}
