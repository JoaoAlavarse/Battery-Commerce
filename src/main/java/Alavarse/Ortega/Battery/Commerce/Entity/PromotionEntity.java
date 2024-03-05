package Alavarse.Ortega.Battery.Commerce.Entity;

import Alavarse.Ortega.Battery.Commerce.Enum.PromotionStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "promotion")
@Getter
@Setter
@EqualsAndHashCode(of = "promotionId")
@AllArgsConstructor
@NoArgsConstructor
public class PromotionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String promotionId;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate expirationDate;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDate;

    @Column(nullable = false)
    private Integer percentage;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PromotionStatus status;

    @JsonIgnore
    @ManyToMany(mappedBy = "usedPromotions")
    private Set<UserEntity> users = new HashSet<>();

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
