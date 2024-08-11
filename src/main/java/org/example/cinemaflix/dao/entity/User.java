package org.example.cinemaflix.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.cinemaflix.model.dto.enums.UserStatus;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    @JsonBackReference
    private Customer customer;

    @OneToOne(cascade= CascadeType.ALL,mappedBy = "user")
    @PrimaryKeyJoinColumn
    @JsonManagedReference
    private ForgotPassword forgotPassword;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<PaymentMovie> paymentMovie;

    private String username;

    private String password;

    @Column(columnDefinition = "boolean default true")
    boolean isAccountNonExpired=true;

    @Column(columnDefinition = "boolean default true")
    boolean isAccountNonLocked=true;

    @Column(columnDefinition = "boolean default true")
    boolean isCredentialsNonExpired=true;

    @Column(columnDefinition = "boolean default true")
    private boolean isEnabled=true;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_authorities",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_name",referencedColumnName = "type"))
    private Set<Authority> authorities;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "User status can not be null")
    @Column(name = "status",nullable = false,columnDefinition = "ENUM('ACTIVE','INACTIVE') DEFAULT 'ACTIVE'")
    private UserStatus userStatus=UserStatus.ACTIVE;

    public User(String username,String password){
        this.username=username;
        this.password=password;
    }
}
