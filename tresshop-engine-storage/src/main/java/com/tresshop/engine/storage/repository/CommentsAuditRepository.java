package com.tresshop.engine.storage.repository;

import com.tresshop.engine.storage.entity.CommentsAuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentsAuditRepository extends JpaRepository<CommentsAuditEntity, String> {

    @Query(value = "SELECT * FROM comments_audit c where c.cr_id = ?1", nativeQuery = true)
    Optional<CommentsAuditEntity> findByCrId(String crId);
}
