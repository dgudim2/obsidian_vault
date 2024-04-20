package org.kloud.module.server;

import org.kloud.daos.BasicDAO;
import org.kloud.model.BaseModel;
import org.kloud.utils.ConfigurationSingleton;
import org.springframework.web.bind.annotation.*;

import static org.kloud.utils.Utils.objectFromBytes;
import static org.kloud.utils.Utils.objectToBytes;

// TODO: Dynamically create endpoints for all DAOs
// TODO: Proper exception handling
// TODO: Unify endpoints/take from common config or DAO config or whatever

@RestController
@RequestMapping("/api")
public class RequestController {

    private enum ResponseOpt {
        OK, FAIL;

        static String fromBool(boolean res) {
            return (res ? OK : FAIL).name();
        }
    }

    private <T extends BaseModel> byte[] getWrapper(BasicDAO<T> dao) {
        return objectToBytes(dao.getObjects());
    }

    private <T extends BaseModel> String updateWrapper(BasicDAO<T> dao, byte[] bytes) {
        T object = objectFromBytes(bytes);
        if (object == null) {
            return ResponseOpt.FAIL.name();
        }
        return ResponseOpt.fromBool(dao.addOrUpdateObject(object));
    }


    //region Spaghetti 🍝 endpoints

    @GetMapping("users/getall")
    public byte[] getUsers() {
        return getWrapper(ConfigurationSingleton.getStorage().getUserStorage());
    }

    @DeleteMapping("users/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        return ResponseOpt.fromBool(ConfigurationSingleton.getStorage().getUserStorage().removeById(id));
    }

    @PutMapping("users/update")
    public String updateUser(@RequestBody byte[] userBytes) {
        return updateWrapper(ConfigurationSingleton.getStorage().getUserStorage(), userBytes);
    }


    @GetMapping("products/getall")
    public byte[] getProducts() {
        return getWrapper(ConfigurationSingleton.getStorage().getProductStorage());
    }

    @DeleteMapping("products/delete/{id}")
    public String deleteProduct(@PathVariable long id) {
        return ResponseOpt.fromBool(ConfigurationSingleton.getStorage().getProductStorage().removeById(id));
    }

    @PutMapping("products/update")
    public String updateProduct(@RequestBody byte[] productBytes) {
        return updateWrapper(ConfigurationSingleton.getStorage().getProductStorage(), productBytes);
    }


    @GetMapping("orders/getall")
    public byte[] getOrders() {
        return getWrapper(ConfigurationSingleton.getStorage().getOrderStorage());
    }

    @DeleteMapping("orders/delete/{id}")
    public String deleteOrder(@PathVariable long id) {
        return ResponseOpt.fromBool(ConfigurationSingleton.getStorage().getOrderStorage().removeById(id));
    }

    @PutMapping("orders/update")
    public String updateOrder(@RequestBody byte[] orderBytes) {
        return updateWrapper(ConfigurationSingleton.getStorage().getOrderStorage(), orderBytes);
    }


    @GetMapping("warehouses/getall")
    public byte[] getWarehouses() {
        return getWrapper(ConfigurationSingleton.getStorage().getWarehouseStorage());
    }

    @DeleteMapping("warehouses/delete/{id}")
    public String deleteWarehouse(@PathVariable long id) {
        return ResponseOpt.fromBool(ConfigurationSingleton.getStorage().getWarehouseStorage().removeById(id));
    }

    @PutMapping("warehouses/update")
    public String updateWarehouse(@RequestBody byte[] warehouseBytes) {
        return updateWrapper(ConfigurationSingleton.getStorage().getWarehouseStorage(), warehouseBytes);
    }


    @GetMapping("comments/getall")
    public byte[] getComments() {
        return getWrapper(ConfigurationSingleton.getStorage().getCommentStorage());
    }

    @DeleteMapping("comments/delete/{id}")
    public String deleteComment(@PathVariable long id) {
        return ResponseOpt.fromBool(ConfigurationSingleton.getStorage().getCommentStorage().removeById(id));
    }

    @PutMapping("comments/update")
    public String updateComment(@RequestBody byte[] commentBytes) {
        return updateWrapper(ConfigurationSingleton.getStorage().getCommentStorage(), commentBytes);
    }

    //endregion
}
