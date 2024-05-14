package kursinis.viljamas.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TShirt extends Product {
    private String sleeveStyle;
    private String neckline;


    public TShirt(String title, String description, int quantity, String size, String brand, String material, String sleeveStyle, String neckline) {
        super(title, description, quantity, size, brand, material);
        this.sleeveStyle = sleeveStyle;
        this.neckline = neckline;
    }
    @Override
    public String toString() {
        return title + ":" + quantity + " - " + getWarehouse();
    }
}
