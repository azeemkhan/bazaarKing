package com.tresshop.engine.services.mapper;

import com.tresshop.engine.client.likeandcomments.WatchLaterRequest;
import com.tresshop.engine.storage.entity.WatchLaterEntity;
import org.springframework.stereotype.Component;

@Component
public class WatchLaterMapper {
    public WatchLaterEntity populate(WatchLaterRequest watchLaterRequest){
        WatchLaterEntity  watchLaterEntity = new WatchLaterEntity();
        watchLaterEntity.setCustId(watchLaterRequest.getCustomerId());
        watchLaterEntity.setProductId(watchLaterRequest.getProductId());
        return watchLaterEntity;
    }
}
