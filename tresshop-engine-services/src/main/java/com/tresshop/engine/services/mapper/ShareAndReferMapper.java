package com.tresshop.engine.services.mapper;

import com.tresshop.engine.client.rewards.ShareAndReferRequest;
import com.tresshop.engine.storage.entity.ShareAndReferEntity;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class ShareAndReferMapper {

    public ShareAndReferEntity populateShareAndReferEntity(ShareAndReferRequest shareAndReferRequest){
        ShareAndReferEntity shareAndReferEntity = new ShareAndReferEntity();
        shareAndReferEntity.setFromUser(shareAndReferRequest.getFromUser());
        shareAndReferEntity.setToUser(shareAndReferRequest.getToUser());
        shareAndReferEntity.setCode("azeem");
        shareAndReferEntity.setPlatform(shareAndReferRequest.getPlatform());
        shareAndReferEntity.setType(shareAndReferRequest.getType());
        shareAndReferEntity.setLastUpdatedTs(Timestamp.valueOf(LocalDateTime.now()));
        return shareAndReferEntity;
    }
}
