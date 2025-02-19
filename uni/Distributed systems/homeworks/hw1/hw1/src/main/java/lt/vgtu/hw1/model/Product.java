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
    /**
     * The Id.
     */
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    /**
     * The Title.
     */
    @Setter
    protected String title;
    /**
     * The Description.
     */
    @Setter
    protected String description;
    /**
     * The Quantity.
     */
    @Setter
    protected int quantity;
    /**
     * The Price.
     */
    @Setter
    protected float price;
    /**
     * The Comments.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "whichProductCommented", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    protected List<Comment> comments;
    @JsonIgnore
    @ManyToOne()
    private Warehouse warehouse;
    @JsonIgnore
    @ManyToOne
    private Cart cart;
    @ManyToOne
    private Shop shop;

    /**
     * Instantiates a new Product.
     *
     * @param title       the title
     * @param description the description
     * @param quantity    the quantity
     * @param price       the price
     * @param warehouse   the warehouse
     */
    public Product(String title, String description, int quantity, float price, Warehouse warehouse) {
        this.title = title;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.warehouse = warehouse;
    }

    /**
     * Instantiates a new Product.
     *
     * @param toCopy the to copy
     */
    public Product(Product toCopy) {
        this.title = toCopy.getTitle();
        this.description = toCopy.getDescription();
        this.quantity = toCopy.getQuantity();
        this.price = toCopy.getPrice();
        this.warehouse = toCopy.getWarehouse();
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    @XmlElement
    public int getId() {
        return id;
    }

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
     * Gets quantity.
     *
     * @return the quantity
     */
    @XmlElement
    public int getQuantity() {
        return quantity;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    @XmlElement
    public float getPrice() {
        return price;
    }

    /**
     * Gets warehouse.
     *
     * @return the warehouse
     */
    @XmlElement
    public Warehouse getWarehouse() {
        return warehouse;
    }

    /**
     * Sets warehouse.
     *
     * @param warehouse the warehouse
     */
    @XmlIDREF
    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    /**
     * Gets comments.
     *
     * @return the comments
     */
    @XmlElement
    public List<Comment> getComments() {
        return comments;
    }

    /**
     * Sets comments.
     *
     * @param comments the comments
     */
    @XmlIDREF
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * Gets cart.
     *
     * @return the cart
     */
    @XmlElement
    public Cart getCart() {
        return cart;
    }

    /**
     * Sets cart.
     *
     * @param cart the cart
     */
    @XmlIDREF
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    /**
     * Gets shop.
     *
     * @return the shop
     */
    @XmlElement
    public Shop getShop() {
        return shop;
    }

    /**
     * Sets shop.
     *
     * @param shop the shop
     */
    @XmlIDREF
    public void setShop(Shop shop) {
        this.shop = shop;
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
