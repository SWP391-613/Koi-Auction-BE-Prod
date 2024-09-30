package com.swp391.koibe.models;

import com.swp391.koibe.enums.UserStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
@Builder
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="first_name", length = 100)
    private String firstName;

    @Column(name="last_name", length = 100)
    private String lastName;

    @Column(name="phone_number", length = 20)
    private String phoneNumber;

    @Column(name="email",nullable = false, length = 100)
    private String email;

    @Column(name="address", length = 200)
    private String address;

    @Column(name="password", length = 200)
    private String password;

    @Transient //do not save to database, check if needed
    private String confirmPassword;

    @Column(name="is_active", columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean isActive;

    @Column(name="is_subscription", columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isSubscription;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private UserStatus status;

    @Column(name="date_of_birth")
    private Date dob;

    @Column(name="avatar_url")
    private String avatarUrl;

    @Column(name="google_account_id", columnDefinition = "INT(11)")
    private int googleAccountId;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

    @Column(name="account_balance", columnDefinition = "BIGINT(20) DEFAULT 0")
    private long accountBalance;

//    @ManyToOne
//    @JoinColumn(name = "department_id")
//    private Department department;

    //Spring Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_"+getRole().getName().toUpperCase()));
        //authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        return authorityList;
    }

    //why getUserName() is return email
    //because in the UserDetailsService, we use email to find user
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
