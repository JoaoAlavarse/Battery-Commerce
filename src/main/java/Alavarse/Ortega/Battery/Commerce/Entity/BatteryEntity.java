package Alavarse.Ortega.Battery.Commerce.Entity;

import Alavarse.Ortega.Battery.Commerce.Enum.BatteryStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "battery")
@EqualsAndHashCode(of = "batteryId")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BatteryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String batteryId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private BigDecimal value;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BatteryStatus status;

    @JsonIgnore
    @ManyToMany(mappedBy = "batteries")
    private Set<CartEntity> carts = new HashSet<>();


    public BatteryEntity(String name, String description, BigDecimal value, Integer quantity) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.quantity = quantity;
        this.status = BatteryStatus.ACTIVE;
    }

    public BatteryEntity(String name, String description, BigDecimal value, Integer quantity, BatteryStatus status) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.quantity = quantity;
        this.status = status;
    }
}
