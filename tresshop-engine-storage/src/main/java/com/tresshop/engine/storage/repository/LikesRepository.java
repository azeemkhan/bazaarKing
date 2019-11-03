package com.tresshop.engine.storage.repository;

import com.tresshop.engine.storage.entity.LikesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<LikesEntity, String> {
    @Query(value = "SELECT customer_id FROM likes l where l.item_liked_id = ?1 AND l.item_type = ?2", nativeQuery = true)
    Optional<List<String>> findAllUsersLiked(String itemLikedId, String itemType);

    @Query(value = "SELECT COUNT(1) FROM likes l where l.item_liked_id = ?1 AND l.item_type = ?2", nativeQuery = true)
    Optional<Integer> totalLikeCount(String itemLikedId, String itemType);

    @Query(value = "SELECT * FROM likes l where l.item_liked_id = ?1 AND l.item_type = ?2 AND l.customer_id = ?3", nativeQuery = true)
    Optional<LikesEntity> findUserLiked(String itemLikedId, String itemType, String customerId);
}
