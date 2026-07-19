package com.example.NexusOS.enums;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum AuditAction {
    LOGIN,
    LOGOUT,

    CREATE_FOLDER,
    UPDATE_FOLDER,
    DELETE_FOLDER,

    CREATE_FILE,
    UPDATE_FILE,
    DELETE_FILE,

    SHARE_FOLDER,
    REMOVE_SHARE,

    CREATE_ORGANIZATION,
    UPDATE_ORGANIZATION,

    CREATE_USER,
    UPDATE_USER,
    DELETE_USER,

    ASSIGN_ROLE,
    REMOVE_ROLE
}
