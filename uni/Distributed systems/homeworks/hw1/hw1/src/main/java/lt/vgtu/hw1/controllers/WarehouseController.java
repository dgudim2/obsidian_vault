package lt.vgtu.hw1.controllers;

import com.google.gson.Gson;
import lt.vgtu.hw1.model.Manager;
import lt.vgtu.hw1.model.Product;
import lt.vgtu.hw1.model.Warehouse;
import lt.vgtu.hw1.repos.ManagerRepo;
import lt.vgtu.hw1.repos.ProductRepo;
import lt.vgtu.hw1.repos.UserRepo;
import lt.vgtu.hw1.repos.WarehouseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * The type Warehouse controller.
 */
@RestController
public class WarehouseController {

    @Autowired
    private WarehouseRepo warehouseRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ManagerRepo managerRepo;

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
     * Gets all warehouses.
     *
     * @return the all warehouses
     */
    @GetMapping(value = "/getAllWarehouses")
    public @ResponseBody Iterable<Warehouse> getAllWarehouses() {
        return warehouseRepo.findAll();
    }


    /**
     * Create spoiler warehouse.
     *
     * @param warehouse the warehouse
     * @return the warehouse
     */
    @PostMapping(value = "/createWarehouse")
    public @ResponseBody Warehouse createSpoiler(@RequestBody Warehouse warehouse) {
        warehouseRepo.save(warehouse);
        return new Warehouse();
    }

    /**
     * Create warehouse with ids warehouse.
     *
     * @param info the info
     * @return the warehouse
     */
    @PostMapping(value = "/createWarehouseWithIds")
    public @ResponseBody Warehouse createWarehouseWithIds(@RequestBody String info) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(info, Properties.class);

        var address = properties.getProperty("address");
        List<Integer> stockIds = convertStringToIntegerList(properties.getProperty("stock"));
        List<Integer> managerIds = convertStringToIntegerList(properties.getProperty("managers"));

        Warehouse warehouse = new Warehouse();

        warehouse.setAddress(address);

        List<Product> products = new ArrayList<>();
        if (stockIds != null) {
            stockIds.forEach(productId -> {
                Optional<Product> productOptional = productRepo.findById(productId);
                productOptional.ifPresent(product -> {
                    product.setWarehouse(warehouse);
                    products.add(product);
                });
            });

            warehouse.setStock(products);
        }

        List<Manager> managers = new ArrayList<>();
        if (managerIds != null) {

            managerIds.forEach(managerId -> {
                Optional<Manager> managerOptional = managerRepo.findById(managerId);
                managerOptional.ifPresent(manager -> {
                    manager.setWarehouse(warehouse);
                    managers.add(manager);
                });
            });
            warehouse.setManagers(managers);
        }
        warehouseRepo.save(warehouse);
        productRepo.saveAll(products);
        managerRepo.saveAll(managers);

        return warehouse;
    }


    /**
     * Update warehouse object warehouse.
     *
     * @param warehouse the warehouse
     * @return the warehouse
     */
    @PutMapping(value = "/updateWarehouseObject")
    public @ResponseBody Warehouse updateWarehouseObject(@RequestBody Warehouse warehouse) {
        warehouseRepo.save(warehouse);
        return warehouse;
    }

    /**
     * Update warehouse warehouse.
     *
     * @param info the info
     * @return the warehouse
     */
    @PutMapping(value = "/updateWarehouse")
    public @ResponseBody Warehouse updateWarehouse(@RequestBody String info) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(info, Properties.class);

        var id = Integer.parseInt(properties.getProperty("id"));
        var address = properties.getProperty("address");
        List<Integer> stockIds = convertStringToIntegerList(properties.getProperty("stock"));
        List<Integer> managerIds = convertStringToIntegerList(properties.getProperty("managers"));

        Optional<Warehouse> optionalWarehouse = warehouseRepo.findById(id);
        if (optionalWarehouse.isPresent()) {
            Warehouse warehouse = optionalWarehouse.get();
            warehouse.setAddress(address);

            warehouse.getStock().forEach(product -> {
                product.setWarehouse(null);
                productRepo.save(product);
            });

            warehouse.getManagers().forEach(manager -> {
                manager.setWarehouse(null);
                managerRepo.save(manager);
            });


            List<Product> products = new ArrayList<>();
            if (stockIds != null) {
                stockIds.forEach(productId -> {
                    Optional<Product> productOptional = productRepo.findById(productId);
                    productOptional.ifPresent(product -> {
                        product.setWarehouse(warehouse);
                        products.add(product);
                    });
                });

                warehouse.setStock(products);
            }

            List<Manager> managers = new ArrayList<>();
            if (managerIds != null) {

                managerIds.forEach(managerId -> {
                    Optional<Manager> managerOptional = managerRepo.findById(managerId);
                    managerOptional.ifPresent(manager -> {
                        manager.setWarehouse(warehouse);
                        managers.add(manager);
                    });
                });
                warehouse.setManagers(managers);
            }

            warehouseRepo.save(warehouse);
            productRepo.saveAll(products);
            managerRepo.saveAll(managers);
            return warehouse;
        } else {
            throw new RuntimeException("Warehouse with ID " + id + " not found");
        }
    }


    /**
     * Delete warehouse response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping(value = "/deleteWarehouse/{id}")
    public @ResponseBody ResponseEntity<String> deleteWarehouse(@PathVariable(name = "id") int id) {
        Optional<Warehouse> optionalWarehouse = warehouseRepo.findById(id);
        if (optionalWarehouse.isPresent()) {
            Warehouse warehouse = optionalWarehouse.get();
            warehouseRepo.delete(warehouse);
            return ResponseEntity.ok().body("Warehouse with ID " + id + " has been deleted successfully.");
        } else {
            return new ResponseEntity<>("Warehouse with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }

}
