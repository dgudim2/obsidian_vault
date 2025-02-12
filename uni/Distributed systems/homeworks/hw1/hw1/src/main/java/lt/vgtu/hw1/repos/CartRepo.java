package lt.vgtu.hw1.repos;

import lt.vgtu.hw1.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart, Integer> {
}
