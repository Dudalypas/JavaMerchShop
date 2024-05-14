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
public class Cap extends Product {
    private String brimType;
    private String closureType;

    public Cap(String title, String description, int quantity, String size, String brand, String material, String brimType, String closureType) {
        super(title, description, quantity, size, brand, material);
        this.brimType = brimType;
        this.closureType = closureType;
    }
    @Override
    public String toString() {
        return title + ":" + quantity + " - " + getWarehouse();
    }
}
