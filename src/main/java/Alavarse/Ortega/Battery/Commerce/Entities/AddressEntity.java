package Alavarse.Ortega.Battery.Commerce.Entities;

import Alavarse.Ortega.Battery.Commerce.DTOs.AddressDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String number;
    @Column(nullable = false)
    private String neighborhood;
    @Column(nullable = false)
    private String complement;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private String CEP;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public AddressEntity(String address, String number, String neighborhood, String complement, String city, String state, String CEP, UserEntity user) {
        this.address = address;
        this.number = number;
        this.neighborhood = neighborhood;
        this.complement = complement;
        this.city = city;
        this.state = state;
        this.CEP = CEP;
        this.user = user;
    }

    public AddressEntity(AddressDTO data, UserEntity user) {
        this.address = data.address();
        this.number = data.number();
        this.neighborhood = data.neighborhood();
        this.complement = data.complement();
        this.city = data.city();
        this.state = data.state();
        this.CEP = data.CEP();
        this.user = user;
    }
}
