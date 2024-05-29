package Alavarse.Ortega.Battery.Commerce.Entities;

import Alavarse.Ortega.Battery.Commerce.Enums.BatteryStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "battery")
@EqualsAndHashCode(of = "batteryId")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class BatteryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String batteryId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, length = 500)
    private String description;
    @Column(nullable = false)
    private BigDecimal value;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BatteryStatus status;
    @Column(nullable = false)
    private String code;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "battery")
    private List<CartBatteryEntity> carts = new ArrayList<>();


    public BatteryEntity(String name, String description, BigDecimal value, Integer quantity, String code) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.quantity = quantity;
        this.status = BatteryStatus.ACTIVE;
        this.code = code;
    }

    public BatteryEntity(String name, String description, BigDecimal value, Integer quantity, BatteryStatus status, String code) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.quantity = quantity;
        this.status = status;
        this.code = code;
    }
}
