package com.example.NexusOS.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(
        name = "organization_members",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_organization_member",
                        columnNames = {"user_id", "organization_id"}
                )
        }
)
public class OrganizationMember extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(nullable = false)
    private Instant joinedAt;

    @Column(nullable = false, length = 100)
    private String invitedBy;

    @Column(nullable = false)
    private boolean active = true;

    public OrganizationMember() {
    }

    public OrganizationMember(User user,
                              Organization organization,
                              Role role,
                              Instant joinedAt,
                              String invitedBy,
                              boolean active) {
        this.user = user;
        this.organization = organization;
        this.role = role;
        this.joinedAt = joinedAt;
        this.invitedBy = invitedBy;
        this.active = active;
    }

    @PrePersist
    public void prePersist() {
        if (joinedAt == null) {
            joinedAt = Instant.now();
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Instant joinedAt) {
        this.joinedAt = joinedAt;
    }

    public String getInvitedBy() {
        return invitedBy;
    }

    public void setInvitedBy(String invitedBy) {
        this.invitedBy = invitedBy;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
