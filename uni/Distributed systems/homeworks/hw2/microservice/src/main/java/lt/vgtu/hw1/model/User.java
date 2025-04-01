package lt.vgtu.hw1.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlIDREF;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public abstract class User implements Serializable {
    @OneToMany(mappedBy = "commentOwner", cascade = CascadeType.ALL)
    @Setter
    @Getter
    protected List<Comment> comments;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private int id;
    private String name;
    @Setter
    @Getter
    private String surname;
    @Setter
    @Getter
    private String login;
    @Setter
    @Getter
    private String password;
    @Getter
    @ManyToOne
    private Shop shop;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @Setter
    @Getter
    private List<Cart> myPurchases;

    /**
     * Instantiates a new User.
     */
    public User(String name, String surname, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
    }

    @XmlIDREF
    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @XmlID
    public String getXId() {
        return id + "";
    }
}
