package Alavarse.Ortega.Battery.Commerce.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "address")
@EqualsAndHashCode(of = "addressId")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String addressId;
    private String userId;
    private String address;
    private String number;
    private String complement;
    private String city;
    private String state;
    private String CEP;

    public AddressEntity(String userId, String address, String number, String complement, String city, String state, String CEP) {
        this.userId = userId;
        this.address = address;
        this.number = number;
        this.complement = complement;
        this.city = city;
        this.state = state;
        this.CEP = CEP;
    }
}
