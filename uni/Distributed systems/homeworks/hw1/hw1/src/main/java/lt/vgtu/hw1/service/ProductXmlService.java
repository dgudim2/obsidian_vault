package lt.vgtu.hw1.service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.RequiredArgsConstructor;
import lt.vgtu.hw1.model.Product;
import lt.vgtu.hw1.repos.ProductRepo;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductXmlService {

    private final ProductRepo productRepo;

    public String saveXml(int productId, String path) throws JAXBException {
        Optional<Product> productOptional = productRepo.findById(productId);
        if (productOptional.isPresent()) {
            JAXBContext context = JAXBContext.newInstance(Product.class);
            Marshaller mar = context.createMarshaller();
            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            mar.marshal(productOptional.get(), new File(path));
            return path;
        } else {
            return null;
        }
    }

}
