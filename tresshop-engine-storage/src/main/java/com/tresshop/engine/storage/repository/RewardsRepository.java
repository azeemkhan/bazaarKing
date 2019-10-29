package com.tresshop.engine.storage.repository;

import com.tresshop.engine.storage.entity.RewardsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardsRepository extends JpaRepository<RewardsEntity, String> {
}
