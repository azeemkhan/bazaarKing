package com.tresshop.engine.services.mapper;

import com.tresshop.engine.client.likeandcomments.LikesRequest;
import com.tresshop.engine.storage.entity.LikesEntity;
import org.springframework.stereotype.Component;

@Component
public class LikesMapper {
    public LikesEntity populate(LikesRequest likesRequest){
        LikesEntity likesEntity = new LikesEntity();
        likesEntity.setCustomerId(likesRequest.getCustomerId());
        likesEntity.setItemLikedId(likesRequest.getItemLikedId());
        likesEntity.setItemType(likesRequest.getItemType());
        return likesEntity;
    }
}
