package lt.vgtu.hw1.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlIDREF;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    @Setter
    @ManyToOne
    private User customer;
    @Setter
    @ManyToOne
    private Manager manager;

    /**
     * Instantiates a new Cart.
     */
    public Cart(User customer, Manager manager, List<Product> items) {
        this.customer = customer;
        this.manager = manager;
        this.itemsToBuy = items;
    }

    @XmlElement
    public int getId() {
        return id;
    }

    @XmlElement
    public List<Product> getItemsToBuy() {
        return itemsToBuy;
    }

    @XmlIDREF
    public void setItemsToBuy(List<Product> itemsToBuy) {
        this.itemsToBuy = itemsToBuy;
    }

    @XmlElement
    public User getCustomer() {
        return customer;
    }

    @XmlElement
    public Manager getManager() {
        return manager;
    }

    @XmlID
    public String getXId() {
        return id + "";
    }
}
