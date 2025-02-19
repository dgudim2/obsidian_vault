package lt.vgtu.hw1.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


/**
 * The type Shop.
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@XmlRootElement(name = "shop")
public class Shop implements Serializable {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Setter
    private String title;
    @Setter
    private String description;
    @Setter
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<User> sysUsers;
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Product> sysProducts;

    @XmlElement
    public String getTitle() {
        return title;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    @XmlElement(name = "user")
    public List<User> getSysUsers() {
        return sysUsers;
    }

    @XmlElement(name = "product")
    public List<Product> getSysProducts() {
        return sysProducts;
    }

    @XmlIDREF
    public void setSysProducts(List<Product> sysProducts) {
        this.sysProducts = sysProducts;
    }

    @XmlID
    public String getXId() {
        return id + "";
    }
}
