package lt.vgtu.hw1.controllers;

import lombok.RequiredArgsConstructor;
import lt.vgtu.hw1.model.*;
import lt.vgtu.hw1.repos.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepo productRepo;
    private final WarehouseRepo warehouseRepo;
    private final CartRepo cartRepo;

    @GetMapping(value = "/getAllProducts")
    public @ResponseBody Iterable<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @PostMapping(value = "/createProduct")
    public @ResponseBody void createProduct(@RequestBody Product product) {
        productRepo.save(product);
    }

    @DeleteMapping(value = "/deleteProduct/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "id") int id) {
        Optional<Product> productOptional = productRepo.findById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            if (product.getCart() != null) {
                Cart cart = product.getCart();
                cart.getItemsToBuy().remove(product);
                cartRepo.save(cart);
            }

            if (product.getWarehouse() != null) {
                Warehouse warehouse = product.getWarehouse();
                warehouse.getStock().remove(product);
                warehouseRepo.save(warehouse);
            }

            productRepo.deleteById(id);

            return ResponseEntity.ok().body("Product with ID " + id + " has been deleted successfully.");
        } else {
            return new ResponseEntity<>("Product with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }
}
