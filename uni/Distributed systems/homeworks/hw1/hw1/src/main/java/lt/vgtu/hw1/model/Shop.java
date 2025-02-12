package lt.vgtu.hw1.model;

import jakarta.persistence.*;
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
public class Shop implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    protected String title;
    protected String description;
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    public List<User> sysUsers;
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    public List<Product> sysProducts;

}
