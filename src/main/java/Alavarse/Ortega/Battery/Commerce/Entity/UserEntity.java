package Alavarse.Ortega.Battery.Commerce.Entity;

import Alavarse.Ortega.Battery.Commerce.DTO.UpdateUserDTO;
import Alavarse.Ortega.Battery.Commerce.Enum.UserRole;
import Alavarse.Ortega.Battery.Commerce.Enum.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

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
    private String email;
    private String password;
    private String name;
    private String document;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Enumerated(EnumType.STRING)
    private UserStatus status;

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
        this.password = data.password();
        this.name = data.name();
        this.status = data.status();
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
        return true;
    }
}
