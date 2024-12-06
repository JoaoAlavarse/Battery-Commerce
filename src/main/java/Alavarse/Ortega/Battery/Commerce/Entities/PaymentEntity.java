package Alavarse.Ortega.Battery.Commerce.Entities;

import Alavarse.Ortega.Battery.Commerce.Enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "payment")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "paymentId")
public class PaymentEntity {
    @Id
    private String paymentId;
    @Column(nullable = false)
    private String description;
    @Column(nullable = true)
    private String url;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @JsonIgnore
    @OneToOne(mappedBy = "payment")
    private SaleEntity sale;

    public PaymentEntity(String paymentId, String url, String description, PaymentStatus status) {
        this.paymentId = paymentId;
        this.url = url;
        this.description = description;
        this.status = status;
    }
}
