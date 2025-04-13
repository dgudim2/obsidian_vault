package lt.vgtu.client.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClientService {

    private final RestTemplate restTemplate;

    public ClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getBook(Long id) {
        return restTemplate.getForObject("http://microservice/productToXml/" + id, String.class);
    }

    public String getServerInstanceInfo() {
        return restTemplate.getForObject("http://microservice/instance-info", String.class);
    }
}
