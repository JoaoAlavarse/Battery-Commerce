package Alavarse.Ortega.Battery.Commerce.Entities;

import Alavarse.Ortega.Battery.Commerce.Enums.PaymentStatus;
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
    @Column(nullable = false)
    private PaymentStatus status;

    @OneToOne(mappedBy = "payment")
    private SaleEntity sale;

    public PaymentEntity(String paymentId, String description, PaymentStatus status) {
        this.paymentId = paymentId;
        this.description = description;
        this.status = status;
    }
}
