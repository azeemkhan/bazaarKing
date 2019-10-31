package com.tresshop.engine.services.mapper;

import com.tresshop.engine.client.enums.RewardTypes;
import com.tresshop.engine.client.rewards.ShareAndReferRequest;
import com.tresshop.engine.storage.entity.ShareAndReferEntity;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class ShareAndReferMapper {

    public ShareAndReferEntity populateShareAndReferEntity(
            ShareAndReferRequest shareAndReferRequest, String code, RewardTypes type, int rewardPoints) {
        ShareAndReferEntity shareAndReferEntity = new ShareAndReferEntity();
        shareAndReferEntity.setFromUser(shareAndReferRequest.getFromUser());
        shareAndReferEntity.setToUser(shareAndReferRequest.getToUser());
        shareAndReferEntity.setCode(code);
        shareAndReferEntity.setPlatform(shareAndReferRequest.getPlatform());
        shareAndReferEntity.setType(type.getName());
        shareAndReferEntity.setPoints(rewardPoints);
        shareAndReferEntity.setLastUpdatedTs(Timestamp.valueOf(LocalDateTime.now()));
        return shareAndReferEntity;
    }
}
