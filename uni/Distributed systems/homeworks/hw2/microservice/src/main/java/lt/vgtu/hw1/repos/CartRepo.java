package lt.vgtu.hw1.repos;

import lt.vgtu.hw1.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Cart repo.
 */
public interface CartRepo extends JpaRepository<Cart, Integer> {
}
