package com.tresshop.engine.storage.repository;

import com.tresshop.engine.storage.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, String> {

    @Query(value = "SELECT * FROM product_category p where p.vendor_type = ?1 AND p.category_name = ?2", nativeQuery = true)
    Optional<ProductCategoryEntity> findCategoryByVendorAndCategoryName(String vendorType, String categoryName);

    @Query(value = "DELETE FROM product_category p where p.vendor_type = ?1 AND p.category_name = ?2", nativeQuery = true)
    Optional<ProductCategoryEntity> deleteCategoryByVendorAndCategoryName(String vendorType, String categoryName);

    @Query(value = "SELECT DISTINCT vendor_type FROM product_category", nativeQuery = true)
    Optional<List<String>> getAllVendorType();

    @Query(value = "SELECT DISTINCT category_name FROM product_category", nativeQuery = true)
    Optional<List<String>> getAllCategory();
}
