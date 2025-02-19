package lt.vgtu.hw1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


/**
 * The type Product.
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@XmlRootElement
public class Product implements Serializable {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Setter
    protected String title;

    @Setter
    protected String description;

    @Setter
    protected int quantity;

    @Setter
    protected float price;

    @Setter
    @OneToMany(mappedBy = "whichProductCommented", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    protected List<Comment> comments;
    @ManyToOne()
    @Setter
    private Warehouse warehouse;
    @Setter
    @ManyToOne
    private Cart cart;
    @Setter
    @ManyToOne
    private Shop shop;

    /**
     * Instantiates a new Product.
     */
    public Product(String title, String description, int quantity, float price, Warehouse warehouse) {
        this.title = title;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.warehouse = warehouse;
    }

    /**
     * Instantiates a new Product (copy constructor)
     */
    public Product(Product toCopy) {
        this.title = toCopy.getTitle();
        this.description = toCopy.getDescription();
        this.quantity = toCopy.getQuantity();
        this.price = toCopy.getPrice();
        this.warehouse = toCopy.getWarehouse();
    }

    @XmlElement
    public int getId() {
        return id;
    }

    @XmlElement
    public String getTitle() {
        return title;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    @XmlElement
    public int getQuantity() {
        return quantity;
    }

    @XmlElement
    public float getPrice() {
        return price;
    }

    @XmlElement
    public Warehouse getWarehouse() {
        return warehouse;
    }

    @XmlElement(name = "comments")
    public List<Comment> getComments() {
        return comments;
    }

    @XmlElement
    public Cart getCart() {
        return cart;
    }

    @XmlElement
    public Shop getShop() {
        return shop;
    }

    @XmlID
    public String getXId() {
        return id + "";
    }
}
