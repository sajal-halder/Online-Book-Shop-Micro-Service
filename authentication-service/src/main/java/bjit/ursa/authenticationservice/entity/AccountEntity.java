package bjit.ursa.authenticationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



@Entity
@Table(name = "accounts_credentials")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountEntity implements UserDetails{
    @Id
    @GeneratedValue
    private Long userId;
    private String email;
    private String password;

    @OneToMany(targetEntity = RoleEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "u_fk" , referencedColumnName = "id")
    private List<RoleEntity> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roles.roleName()));
    }

    @Override
    public String getUsername() {
        return this.email;
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
