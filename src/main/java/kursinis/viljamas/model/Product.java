package kursinis.viljamas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    protected String title;
    protected String description;
    protected int quantity;
    protected String size;
    protected String brand;
    protected String material;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    protected List<Comment> comments;
    private LocalDate dateCreated;
    @ManyToOne
    private Cart cart;
    @ManyToOne
    private Warehouse warehouse;


    public Product(String title, String description, int quantity, String size, String brand, String material) {
        this.title = title;
        this.description = description;
        this.quantity = quantity;
        this.size = size;
        this.brand = brand;
        this.material = material;
    }
    @Override
    public String toString() {
        return String.valueOf(warehouse);
    }
}
