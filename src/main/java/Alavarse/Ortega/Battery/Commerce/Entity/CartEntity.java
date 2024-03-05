package Alavarse.Ortega.Battery.Commerce.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart")
@EqualsAndHashCode(of = "cartId")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String cartId;


}
