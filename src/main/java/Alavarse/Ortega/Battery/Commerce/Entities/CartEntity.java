package Alavarse.Ortega.Battery.Commerce.Entities;

import Alavarse.Ortega.Battery.Commerce.Enums.CartStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
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

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER)
    private Set<CartBatteryEntity> batteries = new HashSet<>();


    public CartEntity(UserEntity user) {
        this.creationDate = LocalDate.now();
        this.user = user;
        this.status = CartStatus.OPENED;
        this.totalValue = BigDecimal.ZERO;
    }

    public CartEntity(PromotionEntity promotion, Set<CartBatteryEntity> batteries) {
        this.promotion = promotion;
        this.batteries = batteries;
    }

    public CartEntity(Set<CartBatteryEntity> batteries) {
        this.batteries = batteries;
    }
}