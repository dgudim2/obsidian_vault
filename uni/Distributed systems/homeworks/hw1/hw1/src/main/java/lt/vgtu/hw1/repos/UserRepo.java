package lt.vgtu.hw1.repos;

import lt.vgtu.hw1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {

}
