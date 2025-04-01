package lt.vgtu.hw1;

import jakarta.xml.bind.JAXBException;
import lt.vgtu.hw1.service.ProductXmlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootTest(classes = Application.class)
class ProductXmlServiceTest {

    @Autowired
    private ProductXmlService xmlService;

    @Test
    void shouldGenerateXmlWhenValidId() throws IOException, JAXBException {
        String res = xmlService.saveXml(1, "test-product.xml");

        assert !res.isEmpty();

        var xml = Files.readString(Path.of(res));

        assert xml.contains("<?xml version");
        assert xml.contains("<product>");
    }


    @Test
    void shouldFailWhenInvalidId() {
        try {
            xmlService.saveXml(-1, "test-product.xml");
        } catch (JAXBException | IOException e) {
            assert true;
            return;
        }
        assert false;
    }

}
