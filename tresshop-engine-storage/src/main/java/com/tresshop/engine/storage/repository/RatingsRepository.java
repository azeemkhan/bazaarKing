package com.tresshop.engine.storage.repository;

import com.tresshop.engine.storage.entity.RatingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RatingsRepository extends JpaRepository<RatingsEntity, String> {

    @Query(value = "SELECT * FROM ratings r where r.item_rating_on_id = ?1 AND r.item_type = ?2", nativeQuery = true)
    Optional<List<RatingsEntity>> sumOfRatingsOfItem(String itemId, String itemType);

    @Query(value = "SELECT * FROM ratings", nativeQuery = true)
    Optional<List<RatingsEntity>> getAllRatings();

    @Query(value = "SELECT COUNT(1) FROM ratings r where r.item_rating_on_id = ?1 AND r.item_type = ?2", nativeQuery = true)
    Optional<Long> countOfRatingsOfItem(String itemId, String type);

}
