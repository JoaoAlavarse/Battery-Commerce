package Alavarse.Ortega.Battery.Commerce.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "address")
@EqualsAndHashCode(of = "addressId")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"user"})
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String addressId;
    private String address;
    private String number;
    private String complement;
    private String city;
    private String state;
    private String CEP;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public AddressEntity(String address, String number, String complement, String city, String state, String CEP, UserEntity user) {
        this.address = address;
        this.number = number;
        this.complement = complement;
        this.city = city;
        this.state = state;
        this.CEP = CEP;
        this.user = user;
    }
}
