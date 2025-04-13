package lt.vgtu.client.controller;

import lt.vgtu.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {

    private final ClientService clientService;

    @Autowired
    public WebController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<String> responses = new ArrayList<>();

        for (int i = 0; i < 1; i++) {
            String response = clientService.getServerInstanceInfo();
            responses.add(response);
        }

        model.addAttribute("responses", responses);

        return "index";
    }
}
