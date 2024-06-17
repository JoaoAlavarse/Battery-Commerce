package Alavarse.Ortega.Battery.Commerce.Entities;

import Alavarse.Ortega.Battery.Commerce.DTOs.Address.AddressDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "address")
@EqualsAndHashCode(of = "addressId")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
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
    @Column(nullable = true)
    private String complement;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private String CEP;
    @Column(nullable = false)
    private Boolean main;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public AddressEntity(String address, String number, String neighborhood, String complement, String city, String state, String CEP, Boolean main, UserEntity user) {
        this.address = address;
        this.number = number;
        this.neighborhood = neighborhood;
        this.complement = complement;
        this.city = city;
        this.state = state;
        this.CEP = CEP;
        this.main = main;
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
        this.main = data.main();
        this.user = user;
    }
}
