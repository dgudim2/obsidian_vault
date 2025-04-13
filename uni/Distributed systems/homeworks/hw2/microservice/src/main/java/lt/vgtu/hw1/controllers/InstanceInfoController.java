package lt.vgtu.hw1.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InstanceInfoController {
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/instance-info")
    public String instanceInfo() {
        return "Book Microservice running on port: " + serverPort;
    }
}
