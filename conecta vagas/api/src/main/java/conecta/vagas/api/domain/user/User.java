
package conecta.vagas.api.domain.user;

import conecta.vagas.api.domain.jobVacancy.JobV;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "USERS")
@Entity(name = "User")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String password;
    @Column(name = "is_company")
    private boolean is_company;

    // Adicionando o relacionamento com a entidade JobV
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<JobV> jobVs = new HashSet<>();

    public User(Long id) {
        this.id = id;
    }

    public User(UserDataRegister userDataRegister) {
        this.name = userDataRegister.name();
        this.email = userDataRegister.email();
        this.password = userDataRegister.password();
        this.is_company = userDataRegister.is_company();
        // Inicializando jobVs como um HashSet vazio
        this.jobVs = new HashSet<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (is_company) {
            return List.of(new SimpleGrantedAuthority("ROLE_COMPANY"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public Boolean getIs_company() {
        return is_company;
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

    // Adicionado setters para os outros campos

    public void setName(String name) {
        this.name = name;
    }

    public void setIs_company(boolean is_company) {
        this.is_company = is_company;
    }

    // Adicionado setter para jobVs

    public void setJobVs(Set<JobV> jobVs) {
        this.jobVs = jobVs;
    }
}