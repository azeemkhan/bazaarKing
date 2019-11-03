package com.tresshop.engine.storage.repository;

import com.tresshop.engine.storage.entity.CustomersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomersEntity, String> {
    @Query(value = "SELECT * FROM customers c where c.phone_number = ?1 AND c.password = ?2", nativeQuery = true)
    Optional<CustomersEntity> findCustomerByPhoneNumberAndPassword(String phoneNumber, String password);
}
