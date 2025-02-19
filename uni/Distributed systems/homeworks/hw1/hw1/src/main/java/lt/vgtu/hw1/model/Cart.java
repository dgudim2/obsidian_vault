package lt.vgtu.hw1.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlIDREF;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * The type Cart.
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<Product> itemsToBuy;
    @ManyToOne
    private User customer;
    @ManyToOne
    private Manager manager;

    /**
     * Instantiates a new Cart.
     *
     * @param customer the customer
     * @param manager  the manager
     * @param items    the items
     */
    public Cart(User customer, Manager manager, List<Product> items) {
        this.customer = customer;
        this.manager = manager;
        this.itemsToBuy = items;
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
     * Gets items to buy.
     *
     * @return the items to buy
     */
    @XmlElement
    public List<Product> getItemsToBuy() {
        return itemsToBuy;
    }

    /**
     * Sets items to buy.
     *
     * @param itemsToBuy the items to buy
     */
    @XmlIDREF
    public void setItemsToBuy(List<Product> itemsToBuy) {
        this.itemsToBuy = itemsToBuy;
    }

    /**
     * Gets customer.
     *
     * @return the customer
     */
    @XmlElement
    public User getCustomer() {
        return customer;
    }

    /**
     * Sets customer.
     *
     * @param customer the customer
     */
    @XmlIDREF
    public void setCustomer(User customer) {
        this.customer = customer;
    }

    /**
     * Gets manager.
     *
     * @return the manager
     */
    @XmlElement
    public Manager getManager() {
        return manager;
    }

    /**
     * Sets manager.
     *
     * @param manager the manager
     */
    @XmlIDREF
    public void setManager(Manager manager) {
        this.manager = manager;
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
