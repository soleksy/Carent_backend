package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(name = "email", columnNames = { "email" })
})
public class UserEntity implements Serializable {

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
    private String password;
    @Getter
    @Setter
    @Column(name = "email")
    private String email;

    @Getter
    @Setter
    @JsonIgnore
    @Column(name = "role_id")
    private Integer roleId;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH})
    @JoinColumn(name = "role_id", insertable=false, updatable=false)
    private RoleEntity role;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH})
    private Set<RentalEntity> rentals;

    @Transient
    private Set<SimpleGrantedAuthority> authorities;

    public UserEntity() {
    }

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void addRental(RentalEntity rental){
        if (rentals == null){
            rentals = new HashSet<>();
        }
        rentals.add(rental);
        rental.setUser(this);
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }
}
