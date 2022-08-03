package uz.jl.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.Role;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, columnDefinition = "bool default true")
    private boolean active;

    @ManyToMany(targetEntity = AuthRole.class, cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = @JoinColumn(name = "auth_user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "auth_role_id", referencedColumnName = "id")
    )
    private List<AuthRole> roles = new ArrayList<>();

}
