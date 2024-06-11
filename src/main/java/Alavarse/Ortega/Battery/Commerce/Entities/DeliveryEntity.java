package Alavarse.Ortega.Battery.Commerce.Entities;

import Alavarse.Ortega.Battery.Commerce.Enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "delivery")
@EqualsAndHashCode(of = "deliveryId")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String deliveryId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String number;
    @Column(nullable = false)
    private String neighborhood;
    @Column(nullable = true)
    private String complement;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private String CEP;

    @OneToOne
    @JoinColumn(name = "cartId", nullable = false)
    private SaleEntity sale;

    public DeliveryEntity(String address, String number, String neighborhood, String complement, String city, String state, String CEP, SaleEntity sale) {
        this.status = DeliveryStatus.CONFIRMANDO;
        this.address = address;
        this.number = number;
        this.neighborhood = neighborhood;
        this.complement = complement;
        this.city = city;
        this.state = state;
        this.CEP = CEP;
        this.sale = sale;
    }
}
