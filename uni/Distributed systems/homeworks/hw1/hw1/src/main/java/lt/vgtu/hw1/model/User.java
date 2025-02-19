package lt.vgtu.hw1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * The type User.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public abstract class User implements Serializable {
    /**
     * The Comments.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "commentOwner", cascade = CascadeType.ALL)
    protected List<Comment> comments;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String login;
    private String password;
    @ManyToOne
    private Shop shop;
    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Cart> myPurchases;


    /**
     * Instantiates a new User.
     *
     * @param name     the name
     * @param surname  the surname
     * @param login    the login
     * @param password the password
     */
    public User(String name, String surname, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
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
