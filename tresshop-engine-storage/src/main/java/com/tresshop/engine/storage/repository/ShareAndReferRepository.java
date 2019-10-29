package com.tresshop.engine.storage.repository;

import com.tresshop.engine.storage.entity.ShareAndReferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShareAndReferRepository extends JpaRepository<ShareAndReferEntity, String> {
}
