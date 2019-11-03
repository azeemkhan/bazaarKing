package com.tresshop.engine.storage.repository;

import com.tresshop.engine.storage.entity.WatchLaterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WatchLaterRepository extends JpaRepository<WatchLaterEntity, String> {

    @Query(value = "SELECT wl.product_id FROM watch_later wl where wl.cust_id = ?1", nativeQuery = true)
    Optional<List<String>> findAllProductsWatchLaterByUser(String custId);
}
