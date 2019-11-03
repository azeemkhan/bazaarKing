package com.tresshop.engine.storage.repository;

import com.tresshop.engine.storage.entity.CommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentsRepository extends JpaRepository<CommentsEntity, String> {

    @Query(value = "SELECT * FROM comments c where c.item_comments_on_id = ?1 AND c.item_type = ?2", nativeQuery = true)
    Optional<List<CommentsEntity>> getAllComments(String itemId, String itemType);

    @Query(value = "SELECT * FROM comments c where c.cr_id = ?1", nativeQuery = true)
    Optional<CommentsEntity> findByCrId(String crId);
}
