package lt.vgtu.hw1.repos;

import lt.vgtu.hw1.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Customer repo.
 */
public interface CustomerRepo extends JpaRepository<Customer, Integer> {

}
