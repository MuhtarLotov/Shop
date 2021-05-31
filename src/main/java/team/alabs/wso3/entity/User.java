package team.alabs.wso3.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String login;
    private String password;

    @ManyToMany
    @JoinTable(name = "user_roles", inverseJoinColumns = {@JoinColumn(name = "role")})
    private Set<Role> roles;


    public boolean hasRole(String role) {
        return getRoles().stream().anyMatch(itm -> itm.getRole().equals(role));
    }
}
