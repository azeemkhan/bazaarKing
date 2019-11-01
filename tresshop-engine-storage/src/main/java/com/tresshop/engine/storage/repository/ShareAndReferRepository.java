package com.tresshop.engine.storage.repository;

import com.tresshop.engine.storage.entity.ShareAndReferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShareAndReferRepository extends JpaRepository<ShareAndReferEntity, String> {

    @Query(value = "SELECT * FROM share_and_refer t where t.from_user = ?1 AND t.type = ?2", nativeQuery = true)
    Optional<List<ShareAndReferEntity>> findUserDataByType(String userId, String type);

    @Query(value = "SELECT COUNT(1) FROM share_and_refer t where t.from_user = ?1 AND t.type = ?2", nativeQuery = true)
    Integer findCountUserDataByType(String userId, String type);

    @Query(value = "SELECT * FROM share_and_refer t where t.from_user = ?1 AND t.to_user = ?2 AND t.code = ?3", nativeQuery = true)
    Optional<ShareAndReferEntity> findReferredUser(String fromUserId, String toUserId, String code);

    @Query(value = "SELECT * FROM share_and_refer t where t.to_user = ?1 AND t.code = ?2", nativeQuery = true)
    Optional<ShareAndReferEntity> findUserWithReferCode(String toUserId, String code);
}
