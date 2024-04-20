package org.kloud.module.server;

import org.kloud.daos.BasicDAO;
import org.kloud.model.BaseModel;
import org.kloud.utils.Conf;
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
        return getWrapper(Conf.getStorage().getUserStorage());
    }

    @DeleteMapping("users/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        return ResponseOpt.fromBool(Conf.getStorage().getUserStorage().removeById(id));
    }

    @PutMapping("users/update")
    public String updateUser(@RequestBody byte[] userBytes) {
        return updateWrapper(Conf.getStorage().getUserStorage(), userBytes);
    }


    @GetMapping("products/getall")
    public byte[] getProducts() {
        return getWrapper(Conf.getStorage().getProductStorage());
    }

    @DeleteMapping("products/delete/{id}")
    public String deleteProduct(@PathVariable long id) {
        return ResponseOpt.fromBool(Conf.getStorage().getProductStorage().removeById(id));
    }

    @PutMapping("products/update")
    public String updateProduct(@RequestBody byte[] productBytes) {
        return updateWrapper(Conf.getStorage().getProductStorage(), productBytes);
    }


    @GetMapping("ordered_products/getall")
    public byte[] getOrderedProducts() {
        return getWrapper(Conf.getStorage().getOrderedProductStorage());
    }

    @DeleteMapping("ordered_products/delete/{id}")
    public String deleteOrderedProduct(@PathVariable long id) {
        return ResponseOpt.fromBool(Conf.getStorage().getOrderedProductStorage().removeById(id));
    }

    @PutMapping("ordered_products/update")
    public String updateOrderedProduct(@RequestBody byte[] productBytes) {
        return updateWrapper(Conf.getStorage().getOrderedProductStorage(), productBytes);
    }


    @GetMapping("orders/getall")
    public byte[] getOrders() {
        return getWrapper(Conf.getStorage().getOrderStorage());
    }

    @DeleteMapping("orders/delete/{id}")
    public String deleteOrder(@PathVariable long id) {
        return ResponseOpt.fromBool(Conf.getStorage().getOrderStorage().removeById(id));
    }

    @PutMapping("orders/update")
    public String updateOrder(@RequestBody byte[] orderBytes) {
        return updateWrapper(Conf.getStorage().getOrderStorage(), orderBytes);
    }


    @GetMapping("warehouses/getall")
    public byte[] getWarehouses() {
        return getWrapper(Conf.getStorage().getWarehouseStorage());
    }

    @DeleteMapping("warehouses/delete/{id}")
    public String deleteWarehouse(@PathVariable long id) {
        return ResponseOpt.fromBool(Conf.getStorage().getWarehouseStorage().removeById(id));
    }

    @PutMapping("warehouses/update")
    public String updateWarehouse(@RequestBody byte[] warehouseBytes) {
        return updateWrapper(Conf.getStorage().getWarehouseStorage(), warehouseBytes);
    }


    @GetMapping("comments/getall")
    public byte[] getComments() {
        return getWrapper(Conf.getStorage().getCommentStorage());
    }

    @DeleteMapping("comments/delete/{id}")
    public String deleteComment(@PathVariable long id) {
        return ResponseOpt.fromBool(Conf.getStorage().getCommentStorage().removeById(id));
    }

    @PutMapping("comments/update")
    public String updateComment(@RequestBody byte[] commentBytes) {
        return updateWrapper(Conf.getStorage().getCommentStorage(), commentBytes);
    }

    //endregion
}
