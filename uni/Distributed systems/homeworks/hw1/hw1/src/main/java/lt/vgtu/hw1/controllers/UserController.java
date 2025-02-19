package lt.vgtu.hw1.controllers;

import com.google.gson.Gson;
import lt.vgtu.hw1.model.Customer;
import lt.vgtu.hw1.model.Manager;
import lt.vgtu.hw1.model.User;
import lt.vgtu.hw1.model.Warehouse;
import lt.vgtu.hw1.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Properties;

/**
 * The type User controller.
 */
@RestController
public class UserController {

    @Autowired
    private ManagerRepo managerRepo;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private WarehouseRepo warehouseRepo;


    /**
     * Gets all managers.
     *
     * @return the all managers
     */
    @GetMapping(value = "/getAllManagers")
    public @ResponseBody Iterable<Manager> getAllManagers() {
        return managerRepo.findAll();
    }

    /**
     * Gets all customer.
     *
     * @return the all customer
     */
    @GetMapping(value = "/getAllCustomers")
    public @ResponseBody Iterable<Customer> getAllCustomer() {
        return customerRepo.findAll();
    }

    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     */
    @GetMapping(value = "/getUserById/{id}")
    public @ResponseBody Optional<User> getUserById(@PathVariable(name = "id") int id) {
        return userRepo.findById(id);
    }


    /**
     * Create manager manager.
     *
     * @param manager the manager
     * @return the manager
     */
    @PostMapping(value = "/createManager")
    public @ResponseBody Manager createManager(@RequestBody Manager manager) {
        managerRepo.save(manager);
        return new Manager();
    }

    /**
     * Create a customer.
     *
     * @param customer the customer to create
     */
    @PostMapping(value = "/createCustomer")
    public void createCustomer(@RequestBody Customer customer) {
        customerRepo.save(customer);
    }


    /**
     * Update the manager
     *
     * @param manager the manager
     * @return the updated manager
     */
    @PutMapping(value = "/updateManagerObject")
    public @ResponseBody Manager updateManagerObject(@RequestBody Manager manager) {
        managerRepo.save(manager);
        return manager;
    }

    /**
     * Update manager as json.
     *
     * @param info the manager json string
     * @return the updated manager
     */
    @PutMapping(value = "/updateManager")
    public @ResponseBody Manager updateManager(@RequestBody String info) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(info, Properties.class);

        var id = Integer.parseInt(properties.getProperty("id"));
        var name = properties.getProperty("name");
        var surname = properties.getProperty("surname");
        var login = properties.getProperty("login");
        var password = properties.getProperty("password");


        Optional<Manager> optionalManager = managerRepo.findById(id);
        if (optionalManager.isPresent()) {
            Manager manager = optionalManager.get();
            manager.setName(name);
            manager.setSurname(surname);
            manager.setLogin(login);
            manager.setPassword(password);

            return managerRepo.save(manager);
        } else {
            throw new RuntimeException("Manager with ID " + id + " not found");
        }
    }

    /**
     * Update customer object
     *
     * @return the updated customer
     */
    @PutMapping(value = "/updateCustomerObject")
    public @ResponseBody Customer updateCustomerObject(@RequestBody Customer customer) {
        customerRepo.save(customer);
        return customer;
    }

    /**
     * Update customer customer.
     *
     * @param info the info
     * @return the customer
     */
    @PutMapping(value = "/updateCustomer")
    public @ResponseBody Customer updateCustomer(@RequestBody String info) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(info, Properties.class);

        var id = Integer.parseInt(properties.getProperty("id"));
        var name = properties.getProperty("name");
        var surname = properties.getProperty("surname");
        var login = properties.getProperty("login");
        var password = properties.getProperty("password");
        var cardNumber = properties.getProperty("cardNumber");
        var shippingAddress = properties.getProperty("shippingAddress");
        var billingAddress = properties.getProperty("billingAddress");
        var birthDate = properties.getProperty("birthDate");


        Optional<Customer> optionalCustomer = customerRepo.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setName(name);
            customer.setSurname(surname);
            customer.setLogin(login);
            customer.setPassword(password);
            customer.setCardNumber(cardNumber);
            customer.setShippingAddress(shippingAddress);
            customer.setBillingAddress(billingAddress);
            customer.setBirthDate(birthDate);

            Customer updatedCustomer = customerRepo.save(customer);
            return updatedCustomer;
        } else {
            throw new RuntimeException("Customer with ID " + id + " not found");
        }
    }


    /**
     * Delete user response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping(value = "/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") int id) {
        Optional<User> userOptional = userRepo.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            user.getMyPurchases().forEach(cart -> {
                cart.setCustomer(null);
                cartRepo.save(cart);
            });

            if (user instanceof Manager && ((Manager) user).getWarehouse() != null) {
                Warehouse warehouse = ((Manager) user).getWarehouse();
                warehouse.getManagers().remove(user);
                warehouseRepo.save(warehouse);
            }

            userRepo.deleteById(id);

            return ResponseEntity.ok().body("User with ID " + id + " has been deleted successfully.");
        } else {
            return new ResponseEntity<>("User with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }
}
