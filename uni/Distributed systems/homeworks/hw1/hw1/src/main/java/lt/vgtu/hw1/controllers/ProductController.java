package lt.vgtu.hw1.controllers;

import lt.vgtu.hw1.model.*;
import lt.vgtu.hw1.repos.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;
import java.util.Optional;

@RestController
public class ProductController {

    private final ProductRepo productRepo;
    private final WarehouseRepo warehouseRepo;
    private final CartRepo cartRepo;

    public ProductController(ProductRepo productRepo, WarehouseRepo warehouseRepo, CartRepo cartRepo) {
        this.productRepo = productRepo;
        this.warehouseRepo = warehouseRepo;
        this.cartRepo = cartRepo;
    }


    @GetMapping(value = "/getAllProducts")
    public @ResponseBody Iterable<Product> getAllProducts() {return productRepo.findAll();}


    @PostMapping(value = "/createProduct")
    public @ResponseBody void createProduct(@RequestBody Product product){
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

    @DeleteMapping(value = "/mapXml/{id}")
    public ResponseEntity<String> mapXml(@RequestParam(name = "id") int id) throws JAXBException {
        Optional<Product> productOptional = productRepo.findById(id);

        if (productOptional.isPresent()) {

            return ResponseEntity.ok().body(productOptional.get().marshal());
        } else {
            return new ResponseEntity<>("Product with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }

}
