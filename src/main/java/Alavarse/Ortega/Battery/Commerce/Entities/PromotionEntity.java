package Alavarse.Ortega.Battery.Commerce.Entities;

import Alavarse.Ortega.Battery.Commerce.Enums.PromotionStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "promotion")
@Getter
@Setter
@EqualsAndHashCode(of = "promotionId")
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class PromotionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String promotionId;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private Integer percentage;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDate;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate expirationDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PromotionStatus status;

    @JsonIgnore
    @ManyToMany(mappedBy = "usedPromotions")
    private Set<UserEntity> users = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "promotion", fetch = FetchType.LAZY)
    private List<CartEntity> carts;

    public PromotionEntity(LocalDate expirationDate, Integer percentage, String code) {
        this.expirationDate = expirationDate;
        this.startDate = LocalDate.now();
        this.percentage = percentage;
        this.code = code;
        this.status = PromotionStatus.ACTIVE;
    }

    public PromotionEntity(LocalDate expirationDate, Integer percentage, String code, PromotionStatus status) {
        this.expirationDate = expirationDate;
        this.percentage = percentage;
        this.code = code;
        this.status = status;
    }
}
