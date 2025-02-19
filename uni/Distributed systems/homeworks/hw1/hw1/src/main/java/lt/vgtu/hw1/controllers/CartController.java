package lt.vgtu.hw1.controllers;

import com.google.gson.Gson;
import lt.vgtu.hw1.model.Cart;
import lt.vgtu.hw1.model.Manager;
import lt.vgtu.hw1.model.Product;
import lt.vgtu.hw1.model.User;
import lt.vgtu.hw1.repos.CartRepo;
import lt.vgtu.hw1.repos.ManagerRepo;
import lt.vgtu.hw1.repos.ProductRepo;
import lt.vgtu.hw1.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * The type Cart controller.
 */
@RestController
public class CartController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ManagerRepo managerRepo;
    @Autowired
    private CartRepo cartRepo;

    private static List<Integer> convertStringToIntegerList(String input) {
        if (Objects.equals(input, "")) {
            List<Integer> result = new ArrayList<>();
            return result;
        }
        String[] parts = input.split(",");
        List<Integer> result = new ArrayList<>();
        for (String part : parts) {
            result.add(Integer.parseInt(part.trim()));
        }
        return result;
    }

    /**
     * Gets all carts.
     *
     * @return the all carts
     */
    @GetMapping(value = "/getAllCarts")
    public @ResponseBody Iterable<Cart> getAllCarts() {
        return cartRepo.findAll();
    }

    /**
     * Create cart cart.
     *
     * @param cart the cart
     * @return the cart
     */
    @PostMapping(value = "/createCart")
    public @ResponseBody Cart createCart(@RequestBody Cart cart) {
        cartRepo.save(cart);
        return new Cart();
    }

    /**
     * Create warehouse with ids cart.
     *
     * @param info the info
     * @return the cart
     */
    @PostMapping(value = "/createCartWithIds")
    public @ResponseBody Cart createWarehouseWithIds(@RequestBody String info) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(info, Properties.class);

        var customerId = properties.getProperty("customer");
        var managerId = properties.getProperty("manager");
        List<Integer> itemsToBuy = convertStringToIntegerList(properties.getProperty("itemsToBuy"));


        Cart cart = new Cart();

        if (customerId != null && !customerId.isEmpty()) {
            Optional<User> userOptional = userRepo.findById(Integer.valueOf(customerId));
            userOptional.ifPresent(user -> {
                user.getMyPurchases().add(cart);
                cart.setCustomer(user);
            });
        }
        if (managerId != null && !managerId.isEmpty()) {
            Optional<Manager> managerOptional = managerRepo.findById(Integer.valueOf(managerId));
            managerOptional.ifPresent(manager -> {
                manager.getMyResponsibleCarts().add(cart);
                cart.setManager(manager);
            });
        }
        List<Product> products = new ArrayList<>();
        if (itemsToBuy != null) {
            itemsToBuy.forEach(productId -> {
                Optional<Product> productOptional = productRepo.findById(productId);
                productOptional.ifPresent(product -> {
                    product.setCart(cart);
                    products.add(product);
                });
            });
            cart.setItemsToBuy(products);
        }


        cartRepo.save(cart);
        productRepo.saveAll(products);

        return cart;
    }


    /**
     * Update cart object cart.
     *
     * @param cart the cart
     * @return the cart
     */
    @PutMapping(value = "/updateCartObject")
    public @ResponseBody Cart updateCartObject(@RequestBody Cart cart) {
        cartRepo.save(cart);
        return cart;
    }

    /**
     * Update cart cart.
     *
     * @param info the info
     * @return the cart
     */
    @PutMapping(value = "/updateCart")
    public @ResponseBody Cart updateCart(@RequestBody String info) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(info, Properties.class);

        var id = Integer.parseInt(properties.getProperty("id"));
        var customerId = Integer.parseInt(properties.getProperty("customer"));
        var managerId = Integer.parseInt(properties.getProperty("manager"));
        List<Integer> itemsToBuy = convertStringToIntegerList(properties.getProperty("itemsToBuy"));

        Optional<Cart> optionalCart = cartRepo.findById(id);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();

            Optional<User> customerOptional = userRepo.findById(customerId);
            customerOptional.ifPresent(customer -> {
                customer.getMyPurchases().add(cart);
                cart.setCustomer(customer);
            });

            Optional<Manager> managerOptional = managerRepo.findById(managerId);
            managerOptional.ifPresent(manager -> {
                manager.getMyResponsibleCarts().add(cart);
                cart.setManager(manager);
            });

            List<Product> products = new ArrayList<>();
            if (itemsToBuy != null) {
                itemsToBuy.forEach(productId -> {
                    Optional<Product> productOptional = productRepo.findById(productId);
                    productOptional.ifPresent(product -> {
                        product.setCart(cart);
                        products.add(product);
                    });
                });
                cart.setItemsToBuy(products);
            }

            cartRepo.save(cart);
            productRepo.saveAll(products);
            return cart;
        } else {
            throw new RuntimeException("Cart with ID " + id + " not found");
        }
    }

    /**
     * Delete cart response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping(value = "/deleteCart/{id}")
    public @ResponseBody ResponseEntity<String> deleteCart(@PathVariable(name = "id") int id) {
        Optional<Cart> optionalCart = cartRepo.findById(id);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();

            if (cart.getCustomer() != null) {
                User customer = cart.getCustomer();
                customer.getMyPurchases().remove(cart);
                userRepo.save(customer);
            }

            if (cart.getManager() != null) {
                Manager manager = cart.getManager();
                manager.getMyResponsibleCarts().remove(cart);
                managerRepo.save(manager);
            }

            if (!cart.getItemsToBuy().isEmpty()) {
                cart.getItemsToBuy().forEach(product -> {
                    product.setCart(null);
                    productRepo.save(product);
                });
            }

            cartRepo.delete(cart);

            return ResponseEntity.ok().body("Cart with ID " + id + " has been deleted successfully.");
        } else {
            return new ResponseEntity<>("Cart with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }

}
