package lt.vgtu.hw1.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlIDREF;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Warehouse.
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Warehouse {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private List<Product> stock;

    @Setter
    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private List<Manager> managers;

    @Setter
    private String address;

    public Warehouse(String address) {
        this.address = address;
        this.stock = new ArrayList<>();
        this.managers = new ArrayList<>();
    }

    @XmlElement
    public List<Product> getStock() {
        return stock;
    }

    @XmlIDREF
    public void setStock(List<Product> stock) {
        this.stock = stock;
    }

    @XmlElement
    public List<Manager> getManagers() {
        return managers;
    }
    
    @XmlElement
    public String getAddress() {
        return address;
    }

    @XmlID
    private String getXId() {
        return id + "";
    }
}
