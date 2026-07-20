package com.example.NexusOS.entity;

import com.example.NexusOS.enums.AccountStatus;
import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users")
public class User extends BaseEntity{
    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String passwordHash;

    @Column(unique = true, length = 15)
    private String phone;

    @Column(length = 1000)
    private String profileImageUrl;

    @Column(nullable = false)
    private boolean emailVerified = false;

    @Column(nullable = false)
    private boolean phoneVerified = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus accountStatus = AccountStatus.INACTIVE;

    @Column(nullable = false)
    private boolean enabled = false;

    @Column(nullable = false)
    private boolean accountLocked = false;

    @Column(nullable = false)
    private int failedLoginAttempts = 0;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<UserRole> userRoles = new HashSet<>();

    public User() {}

    public User(String firstName,
                String lastName,
                String username,
                String email,
                String passwordHash,
                String phone,
                String profileImageUrl,
                boolean emailVerified,
                boolean phoneVerified,
                AccountStatus accountStatus,
                boolean enabled,
                boolean accountLocked,
                int failedLoginAttempts) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phone = phone;
        this.profileImageUrl = profileImageUrl;
        this.emailVerified = emailVerified;
        this.phoneVerified = phoneVerified;
        this.accountStatus = accountStatus;
        this.enabled = enabled;
        this.accountLocked = accountLocked;
        this.failedLoginAttempts = failedLoginAttempts;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public boolean isPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }
}
