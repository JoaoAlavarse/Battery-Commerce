package Alavarse.Ortega.Battery.Commerce.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "sale")
@EqualsAndHashCode(of = "saleId")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String saleId;

    @Column(nullable = false, updatable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate creationDate;
    @Column(nullable = false)
    private BigDecimal value;
    @Column(nullable = false)
    private BigDecimal freightValue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "cartId", nullable = false)
    private CartEntity cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promotionId")
    private PromotionEntity promotion;

    @OneToOne(mappedBy = "sale")
    private DeliveryEntity delivery;

    public SaleEntity(BigDecimal value, BigDecimal freightValue, UserEntity user, CartEntity cart, PromotionEntity promotion) {
        this.creationDate = LocalDate.now();
        this.value = value;
        this.freightValue = freightValue;
        this.user = user;
        this.cart = cart;
        this.promotion = promotion;
    }
}