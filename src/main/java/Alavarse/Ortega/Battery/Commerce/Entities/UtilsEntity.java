package Alavarse.Ortega.Battery.Commerce.Entities;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "utils")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "utilId")
public class UtilsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer utilId;
    @Column(nullable = false)
    private String key;
    @Column(nullable = false, length = 500)
    private String value;

    public UtilsEntity(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
