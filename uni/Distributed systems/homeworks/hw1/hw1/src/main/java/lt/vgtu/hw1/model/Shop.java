package lt.vgtu.hw1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@XmlRootElement(name = "shop")
public class Shop implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<User> sysUsers;
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Product> sysProducts;

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

    @XmlElement(name = "user")
    public List<User> getSysUsers() {
        return sysUsers;
    }

    @XmlElement(name = "product")
    public List<Product> getSysProducts() {
        return sysProducts;
    }
}
