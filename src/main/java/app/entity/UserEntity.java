package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(name = "email", columnNames = { "email" })
})
public class UserEntity implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column(name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Column(name = "email")
    private String email;
    @JsonIgnore
    @Column(name = "role_id")
    private Integer roleId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH})
    @JoinColumn(name = "role_id", insertable=false, updatable=false)
    private RoleEntity role;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<RentalEntity> rentals;

    @Transient
    private Set<SimpleGrantedAuthority> authorities;

    @JsonIgnore
    public Set<SimpleGrantedAuthority> getAuthorities() {
        if (authorities == null) {
            authorities = new HashSet<>();
            if (role != null) { //TODO can role_id be null ?
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
            }
        }
        return authorities;
    }

    public void addRental(RentalEntity rental){
        if (rentals == null){
            rentals = new HashSet<>();
        }
        rentals.add(rental);
        rental.setUser(this);
    }
}
