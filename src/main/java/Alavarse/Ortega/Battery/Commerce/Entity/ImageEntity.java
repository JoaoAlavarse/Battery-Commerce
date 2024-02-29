package Alavarse.Ortega.Battery.Commerce.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "image")
@EqualsAndHashCode(of = "imageId")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String imageId;
    @Column(nullable = false)
    private byte[] bytes;

    @ManyToOne
    @JoinColumn(name = "batteryId", nullable = false)
    @JsonIgnore
    private BatteryEntity battery;

    public ImageEntity(byte[] bytes, BatteryEntity battery) {
        this.bytes = bytes;
        this.battery = battery;
    }
}
