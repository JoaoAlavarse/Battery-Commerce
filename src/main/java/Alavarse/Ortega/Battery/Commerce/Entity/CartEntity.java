package Alavarse.Ortega.Battery.Commerce.Entity;

import Alavarse.Ortega.Battery.Commerce.Enum.CartStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate creationDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CartStatus status;

    @Column(nullable = false)
    private BigDecimal totalValue;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
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
        this.status = CartStatus.OPENED;
        this.totalValue = BigDecimal.valueOf(0.00);
    }

    public CartEntity(PromotionEntity promotion, Set<BatteryEntity> batteries) {
        this.promotion = promotion;
        this.batteries = batteries;
    }

    public CartEntity(Set<BatteryEntity> batteries) {
        this.batteries = batteries;
    }
}