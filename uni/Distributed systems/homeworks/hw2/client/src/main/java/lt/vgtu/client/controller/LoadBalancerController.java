package lt.vgtu.client.controller;

import lt.vgtu.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/client")
public class LoadBalancerController {
    private final ClientService clientService;

    @Autowired
    public LoadBalancerController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/productToXml/{id}")
    public ResponseEntity<String> getBook(@PathVariable Long id) {
        return new ResponseEntity<>(clientService.getBook(id), HttpStatus.OK);
    }

    @GetMapping("/server-instance")
    public String getServerInstance() {
        return clientService.getServerInstanceInfo();
    }

    @GetMapping("/test-load-balancing")
    public List<String> testLoadBalancing() {
        List<String> responses = new ArrayList<>();

        for (int i = 0; i < 500; i++) {
            String response = clientService.getServerInstanceInfo();
            responses.add("Request " + (i+1) + ": " + response);
        }

        return responses;
    }
}
