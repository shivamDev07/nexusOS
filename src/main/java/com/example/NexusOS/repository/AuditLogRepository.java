package com.example.NexusOS.repository;

import com.example.NexusOS.entity.AuditLog;
import com.example.NexusOS.enums.AuditAction;
import com.example.NexusOS.enums.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {
    List<AuditLog> findByUsername(String username);

    List<AuditLog> findByEntityType(EntityType entityType);

    List<AuditLog> findByAction(AuditAction action);
}
