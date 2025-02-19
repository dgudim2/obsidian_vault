package lt.vgtu.hw1.controllers;

import jakarta.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import lt.vgtu.hw1.service.ProductXmlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ProductXmlController {

    private final ProductXmlService bookToXmlService;

    /**
     * Map a product to xml by id.
     *
     * @param id the id
     * @return   the saved path (200) or 404 if the product lookup failed
     * @throws JAXBException the jaxb exception, if serialization failed (500)
     * @throws IOException   the io exception, if writing to file failed (500)
     */
    @GetMapping(value = "/productToXml/{id}")
    public ResponseEntity<String> mapXml(@PathVariable(name = "id") int id) throws JAXBException, IOException {
        var saved = bookToXmlService.saveXml(id, "./product-" + id + ".xml");
        if (saved == null) {
            return new ResponseEntity<>("Product with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

}
