package lt.vgtu.hw1.controllers;

import jakarta.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import lt.vgtu.hw1.service.ProductXmlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ProductXmlController {

    private final ProductXmlService bookToXmlService;

    @GetMapping(value = "/mapXml/{id}")
    public ResponseEntity<String> mapXml(@PathVariable(name = "id") int id) throws JAXBException {
        var saved = bookToXmlService.saveXml(id, "./product-" + id + ".xml");
        if (saved == null) {
            return new ResponseEntity<>("Product with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

}
