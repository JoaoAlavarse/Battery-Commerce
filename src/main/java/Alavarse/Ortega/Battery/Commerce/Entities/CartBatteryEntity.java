package Alavarse.Ortega.Battery.Commerce.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_battery")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "cart_battery_id")
public class CartBatteryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String cart_battery_id;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "battery_id", nullable = false)
    private BatteryEntity battery;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private CartEntity cart;

    public CartBatteryEntity(Integer quantity, BatteryEntity battery, CartEntity cart) {
        this.quantity = quantity;
        this.battery = battery;
        this.cart = cart;
    }
}
