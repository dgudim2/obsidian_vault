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
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<User> sysUsers;
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Product> sysProducts;

    /**
     * Gets title.
     *
     * @return the title
     */
    @XmlElement
    public String getTitle() {
        return title;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    @XmlElement
    public String getDescription() {
        return description;
    }

    /**
     * Gets sys users.
     *
     * @return the sys users
     */
    @XmlElement(name = "user")
    public List<User> getSysUsers() {
        return sysUsers;
    }

    /**
     * Sets sys users.
     *
     * @param sysUsers the sys users
     */
    @XmlIDREF
    public void setSysUsers(List<User> sysUsers) {
        this.sysUsers = sysUsers;
    }

    /**
     * Gets sys products.
     *
     * @return the sys products
     */
    @XmlElement(name = "product")
    public List<Product> getSysProducts() {
        return sysProducts;
    }

    /**
     * Sets sys products.
     *
     * @param sysProducts the sys products
     */
    @XmlIDREF
    public void setSysProducts(List<Product> sysProducts) {
        this.sysProducts = sysProducts;
    }

    /**
     * Gets x id.
     *
     * @return the x id
     */
    @XmlID
    public String getXId() {
        return id + "";
    }
}
