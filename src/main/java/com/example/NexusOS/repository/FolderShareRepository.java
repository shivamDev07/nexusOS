package com.example.NexusOS.repository;

import com.example.NexusOS.entity.Folder;
import com.example.NexusOS.entity.FolderShare;
import com.example.NexusOS.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FolderShareRepository extends JpaRepository<FolderShare, UUID> {
    List<FolderShare> findBySharedWithAndActiveTrue(User sharedWith);

    List<FolderShare> findByFolder(Folder folder);

    boolean existsByFolderAndSharedWith(Folder folder, User sharedWith);
}
