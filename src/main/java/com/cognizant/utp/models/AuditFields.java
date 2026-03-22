package com.cognizant.utp.models;

import java.time.LocalDateTime;

/**
 * Audit fields are DB-managed.
 * Java only reads them after SELECT.
 */
public abstract class AuditFields {

    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    protected boolean isDeleted;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    // ✅ Used ONLY by DAO during hydration
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // ✅ Used ONLY by DAO during hydration
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}