package Alavarse.Ortega.Battery.Commerce.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
