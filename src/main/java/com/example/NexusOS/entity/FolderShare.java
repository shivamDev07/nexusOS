package com.example.NexusOS.entity;

import com.example.NexusOS.enums.SharePermission;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "folder_shares")
public class FolderShare extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_with_user_id", nullable = false)
    private User sharedWith;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SharePermission permission;

    private Instant expiresAt;

    @Column(nullable = false)
    private Boolean active = true;

    public FolderShare() {
    }

    public FolderShare(Long id, UUID uuid, Instant createdAt, Instant updatedAt, Instant deletedAt, Folder folder, User owner, User sharedWith, SharePermission permission, Instant expiresAt, Boolean active) {
        super(id, uuid, createdAt, updatedAt, deletedAt);
        this.folder = folder;
        this.owner = owner;
        this.sharedWith = sharedWith;
        this.permission = permission;
        this.expiresAt = expiresAt;
        this.active = active;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getSharedWith() {
        return sharedWith;
    }

    public void setSharedWith(User sharedWith) {
        this.sharedWith = sharedWith;
    }

    public SharePermission getPermission() {
        return permission;
    }

    public void setPermission(SharePermission permission) {
        this.permission = permission;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
