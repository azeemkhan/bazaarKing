package com.tresshop.engine.storage.repository;

import com.tresshop.engine.storage.entity.SellersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<SellersEntity, String> {

    @Query(value = "SELECT * FROM sellers s where s.phone_number = ?1 AND s.password = ?2", nativeQuery = true)
    Optional<SellersEntity> findSellerByPhoneNumberAndPassword(String phoneNumber, String password);

}
