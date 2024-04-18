package org.kloud.module.server;

import org.kloud.daos.BasicDAO;
import org.kloud.model.BaseModel;
import org.kloud.model.user.User;
import org.kloud.utils.ConfigurationSingleton;
import org.springframework.web.bind.annotation.*;

import static org.kloud.utils.Utils.objectFromBytes;
import static org.kloud.utils.Utils.objectToBytes;

// TODO: Dynamically create endpoints for all daos
// TODO: Proper exception handling

@RestController
@RequestMapping("/api")
public class RequestController {

    private <T extends BaseModel> byte[] getWrapper(BasicDAO<T> dao) {
        return objectToBytes(dao.getObjects());
    }

    @GetMapping("users/getall")
    public byte[] getUsers() {
        return getWrapper(ConfigurationSingleton.getStorage().getUserStorage());
    }

    @DeleteMapping("users/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        var res = ConfigurationSingleton.getStorage().getUserStorage().removeById(id);
        return res ? "OK" : "FAIL";
    }

    @PutMapping("users/update")
    public String deleteUser(@RequestBody byte[] userBytes) {
        User user = objectFromBytes(userBytes);
        if(user == null) {
            return "FAIL";
        }
        var res = ConfigurationSingleton.getStorage().getUserStorage().addOrUpdateObject(user);
        return res ? "OK" : "FAIL";
    }

    @GetMapping("products/getall")
    public byte[] getProducts() {
        return getWrapper(ConfigurationSingleton.getStorage().getProductStorage());
    }

    @GetMapping("orders/getall")
    public byte[] getOrders() {
        return getWrapper(ConfigurationSingleton.getStorage().getOrdersStorage());
    }

    @GetMapping("warehouses/getall")
    public byte[] getWarehouses() {
        return getWrapper(ConfigurationSingleton.getStorage().getWarehouseStorage());
    }

    @GetMapping("comments/getall")
    public byte[] getComments() {
        return getWrapper(ConfigurationSingleton.getStorage().getCommentStorage());
    }
}
