package com.tresshop.engine.services.mapper;

import com.tresshop.engine.client.likeandcomments.RatingsRequest;
import com.tresshop.engine.storage.entity.RatingsEntity;
import org.springframework.stereotype.Component;

@Component
public class RatingsMapper {
    public RatingsEntity populate(RatingsRequest ratingsRequest){
        RatingsEntity ratingsEntity = new RatingsEntity();
        ratingsEntity.setCustomerId(ratingsRequest.getCustomerId());
        ratingsEntity.setRatings(ratingsRequest.getRating());
        ratingsEntity.setItemRatingOnId(ratingsRequest.getItemRatingOnId());
        ratingsEntity.setItemType(ratingsRequest.getItemType());
        return ratingsEntity;
    }
}
