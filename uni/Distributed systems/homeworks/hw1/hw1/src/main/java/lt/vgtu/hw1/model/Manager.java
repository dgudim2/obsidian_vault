package lt.vgtu.hw1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * The type Manager.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Manager extends User {
    private boolean isAdmin;
    @JsonIgnore
    @ManyToOne
    private Warehouse warehouse;
    @JsonIgnore
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
    private List<Cart> myResponsibleCarts;

    /**
     * Instantiates a new Manager.
     *
     * @param login    the login
     * @param password the password
     * @param name     the name
     * @param surname  the surname
     * @param isAdmin  the is admin
     */
    public Manager(String login, String password, String name, String surname, boolean isAdmin) {
        super(name, surname, login, password);
        this.isAdmin = isAdmin;
    }

}
