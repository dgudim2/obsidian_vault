package lt.vgtu.hw1.repos;

import lt.vgtu.hw1.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepo extends JpaRepository<Warehouse, Integer> {
}
