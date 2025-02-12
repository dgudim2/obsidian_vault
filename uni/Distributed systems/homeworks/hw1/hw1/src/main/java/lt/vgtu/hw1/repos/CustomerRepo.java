package lt.vgtu.hw1.repos;

import lt.vgtu.hw1.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

}
