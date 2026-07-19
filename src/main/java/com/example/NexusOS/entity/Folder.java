package com.example.NexusOS.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "folders")
public class Folder extends BaseEntity{
    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 1000)
    private String path;

    @Column(nullable = false, length = 100)
    private String createdBy;

    @Column(nullable = false)
    private boolean active = true;


    /*
     * Organization that owns this folder
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;


    /*
     * Parent folder
     *
     * Example:
     *
     * Documents
     *     |
     *     |-- Projects
     *             |
     *             |-- NexusOS
     *
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_folder_id")
    private Folder parentFolder;


    /*
     * Child folders
     */
    @OneToMany(
            mappedBy = "parentFolder",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Folder> childFolders = new HashSet<>();


    public Folder() {}

    public Folder(String name,
                  String path,
                  String createdBy,
                  boolean active,
                  Organization organization,
                  Folder parentFolder) {

        this.name = name;
        this.path = path;
        this.createdBy = createdBy;
        this.active = active;
        this.organization = organization;
        this.parentFolder = parentFolder;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getPath() {
        return path;
    }


    public void setPath(String path) {
        this.path = path;
    }


    public String getCreatedBy() {
        return createdBy;
    }


    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


    public boolean isActive() {
        return active;
    }


    public void setActive(boolean active) {
        this.active = active;
    }


    public Organization getOrganization() {
        return organization;
    }


    public void setOrganization(Organization organization) {
        this.organization = organization;
    }


    public Folder getParentFolder() {
        return parentFolder;
    }


    public void setParentFolder(Folder parentFolder) {
        this.parentFolder = parentFolder;
    }


    public Set<Folder> getChildFolders() {
        return childFolders;
    }


    public void setChildFolders(Set<Folder> childFolders) {
        this.childFolders = childFolders;
    }


    public void addChildFolder(Folder folder) {
        childFolders.add(folder);
        folder.setParentFolder(this);
    }


    public void removeChildFolder(Folder folder) {
        childFolders.remove(folder);
        folder.setParentFolder(null);
    }
}
