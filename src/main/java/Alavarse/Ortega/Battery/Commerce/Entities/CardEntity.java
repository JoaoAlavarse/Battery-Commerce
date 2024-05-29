package Alavarse.Ortega.Battery.Commerce.Entities;

import Alavarse.Ortega.Battery.Commerce.DTOs.CardDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "card")
@EqualsAndHashCode(of = "cardId")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String cardId;
    @Column(nullable = false, unique = true, updatable = false)
    private String cardNumber;
    @Column(nullable = false)
    private String cardOwner;
    @Column(nullable = false)
    private String expirationDate;
    @Column(nullable = false)
    private String cvv;
    @Column(nullable = false)
    private String flag;
    @Column(nullable = false)
    private Boolean main;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public CardEntity(String cardNumber, String cardOwner, String expirationDate, String cvv, String flag, UserEntity user, Boolean main) {
        this.cardNumber = cardNumber;
        this.cardOwner = cardOwner;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.flag = flag;
        this.user = user;
        this.main = main;
    }

    public CardEntity(CardDTO data, UserEntity user) {
        this.cardNumber = data.cardNumber();
        this.cardOwner = data.cardOwner();
        this.expirationDate = data.expirationDate();
        this.cvv = data.cvv();
        this.main = data.main();
        this.user = user;
    }
}
