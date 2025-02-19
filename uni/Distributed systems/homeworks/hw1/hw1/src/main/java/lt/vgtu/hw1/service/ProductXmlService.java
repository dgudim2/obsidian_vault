package lt.vgtu.hw1.service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.RequiredArgsConstructor;
import lt.vgtu.hw1.model.Product;
import lt.vgtu.hw1.repos.ProductRepo;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * Product xml service.
 */
@Service
@RequiredArgsConstructor
public class ProductXmlService {

    private final ProductRepo productRepo;

    /**
     * Find and serialize product by id
     *
     * @param productId the product id
     * @param path      where to save the xml
     * @return absolute path to the saved file
     */
    public String saveXml(int productId, String path) throws JAXBException, IOException {
        Optional<Product> productOptional = productRepo.findById(productId);
        if (productOptional.isPresent()) {
            JAXBContext context = JAXBContext.newInstance(Product.class);
            Marshaller mar = context.createMarshaller();
            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            var f = new File(path);
            var absolutePath = f.getCanonicalPath();
            mar.marshal(productOptional.get(), f);
            return absolutePath;
        } else {
            return null;
        }
    }

}
