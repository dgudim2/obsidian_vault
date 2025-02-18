package lt.vgtu.hw1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@XmlRootElement
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    protected String title;
    protected String description;
    protected int quantity;
    protected float price;
    @JsonIgnore
    @ManyToOne()
    @XmlTransient
    private Warehouse warehouse;
    @JsonIgnore
    @OneToMany(mappedBy = "whichProductCommented", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapsId
    protected List<Comment> comments;
    @JsonIgnore
    @ManyToOne
    @MapsId
    private Cart cart;
    @ManyToOne
    @MapsId
    private Shop shop;

    public Product(String title, String description, int quantity, float price, Warehouse warehouse) {
        this.title = title;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.warehouse = warehouse;
    }

    public Product(Product toCopy) {
        this.title = toCopy.getTitle();
        this.description = toCopy.getDescription();
        this.quantity = toCopy.getQuantity();
        this.price = toCopy.getPrice();
        this.warehouse = toCopy.getWarehouse();
    }
}
