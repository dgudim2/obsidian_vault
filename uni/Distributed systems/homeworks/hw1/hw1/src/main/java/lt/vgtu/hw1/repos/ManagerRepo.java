package lt.vgtu.hw1.repos;

import lt.vgtu.hw1.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Manager repo.
 */
public interface ManagerRepo extends JpaRepository<Manager, Integer> {
}
