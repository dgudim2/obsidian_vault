package org.kloud.module.server;

import javafx.util.Pair;
import org.kloud.utils.ConfigurationSingleton;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class RequestController {

    @GetMapping("getusers")
    public List<Map<String, Pair<Object, Class<?>>>> getUsers() {
        // TODO: Pass errors/exceptions, check if running in headless mode in ErrorLogger and don't show errors
        var users = ConfigurationSingleton.getStorage().getUserStorage().getObjects();
        var convertedUsers = new ArrayList<Map<String, Pair<Object, Class<?>>>>(users.size());
        for (var user : users) {
            convertedUsers.add(user.getFieldMap());
        }
        return convertedUsers;
    }
}
