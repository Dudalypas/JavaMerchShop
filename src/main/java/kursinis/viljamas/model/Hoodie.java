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
public final class Hoodie extends Product {
    private String hoodStyle;
    private String PocketStyle;

    public Hoodie(String title, String description, int quantity, String size, String brand, String material, String hoodStyle, String PocketStyle) {
        super(title, description, quantity, size, brand, material);
        this.hoodStyle = hoodStyle;
        this.PocketStyle = PocketStyle;
    }
    @Override
    public String toString() {
        return title + ":" + quantity + " - " + getWarehouse();
    }
}
