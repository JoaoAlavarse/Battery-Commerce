package Alavarse.Ortega.Battery.Commerce.Entity;

import Alavarse.Ortega.Battery.Commerce.Enum.CartStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cart")
@EqualsAndHashCode(of = "cartId")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String cartId;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate creationDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CartStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promotion_id")
    private PromotionEntity promotion;

    @ManyToMany
    @JoinTable(
            name = "cart_batteries",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "battery_id")
    )
    private Set<BatteryEntity> batteries = new HashSet<>();

    public CartEntity(UserEntity user) {
        this.creationDate = LocalDate.now();
        this.user = user;
    }

    public CartEntity(PromotionEntity promotion, Set<BatteryEntity> batteries) {
        this.promotion = promotion;
        this.batteries = batteries;
    }

    public CartEntity(Set<BatteryEntity> batteries) {
        this.batteries = batteries;
    }
}