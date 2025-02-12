package lt.vgtu.hw1.repos;

import lt.vgtu.hw1.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepo extends JpaRepository<Manager, Integer> {
}
