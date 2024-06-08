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
    @Column(nullable = false)
    @JsonIgnore
    private String address;
    @Column(nullable = false)
    @JsonIgnore
    private String number;
    @Column(nullable = false)
    @JsonIgnore
    private String neighborhood;
    @Column(nullable = true)
    @JsonIgnore
    private String complement;
    @Column(nullable = false)
    @JsonIgnore
    private String city;
    @Column(nullable = false)
    @JsonIgnore
    private String state;
    @Column(nullable = false)
    @JsonIgnore
    private String CEP;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "cartId")
    private CartEntity cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promotionId")
    private PromotionEntity promotion;

    public SaleEntity(UserEntity user, CartEntity cart, BigDecimal value, BigDecimal freightValue,
                      String address, String number, String neighborhood, String complement, String city, String state, String CEP) {
        this.creationDate = LocalDate.now();
        this.user = user;
        this.cart = cart;
        this.value = value;
        this.freightValue = freightValue;
        this.address = address;
        this.number = number;
        this.neighborhood = neighborhood;
        this.complement = complement;
        this.city = city;
        this.state = state;
        this.CEP = CEP;
    }
}
