package lt.vgtu.hw1.repos;


import lt.vgtu.hw1.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Product repo.
 */
public interface ProductRepo extends JpaRepository<Product, Integer> {
}
