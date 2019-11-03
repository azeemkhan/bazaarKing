package com.tresshop.engine.storage.repository;

import com.tresshop.engine.storage.entity.RewardsEntity;
import com.tresshop.engine.storage.entity.SystemBannerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SystemBannerRepository extends JpaRepository<SystemBannerEntity, Long> {

    @Query(value = "SELECT * FROM system_banners r where r.start_date < SYSDATE() AND r.end_date > SYSDATE()", nativeQuery = true)
    List<SystemBannerEntity> findAllActiveBanners();
}
