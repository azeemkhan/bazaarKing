package com.tresshop.engine.storage.repository;

import com.tresshop.engine.storage.entity.SystemProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemPropertiesRepository extends JpaRepository<SystemProperty, String> {
}
