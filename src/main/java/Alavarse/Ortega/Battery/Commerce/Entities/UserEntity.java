package Alavarse.Ortega.Battery.Commerce.Entities;

import Alavarse.Ortega.Battery.Commerce.DTOs.UpdateUserDTO;
import Alavarse.Ortega.Battery.Commerce.Enums.UserRole;
import Alavarse.Ortega.Battery.Commerce.Enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "userId")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;
    @Column(nullable = false, unique = true)
    private String email;
    @JsonIgnore
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String document;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<AddressEntity> address;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<CartEntity> carts;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_promotions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "promotion_id")
    )
    private Set<PromotionEntity> usedPromotions = new HashSet<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private Set<CardEntity> cards = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<SaleEntity> sales;

    public UserEntity(String email, String password, String name, String document, UserRole role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.document = document;
        this.role = role;
        this.status = UserStatus.ACTIVE;
    }

    public UserEntity(String email, String password, String name, String document, UserRole role, UserStatus status) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.document = document;
        this.role = role;
        this.status = status;
    }

    public UserEntity(UpdateUserDTO data) {
        this.name = data.name();
        this.email = data.email();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.status.equals(UserStatus.ACTIVE);
    }
}
