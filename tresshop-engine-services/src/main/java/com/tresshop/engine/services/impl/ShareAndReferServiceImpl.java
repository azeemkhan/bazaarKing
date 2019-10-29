package com.tresshop.engine.services.impl;

import com.tresshop.engine.client.rewards.ShareAndReferRequest;
import com.tresshop.engine.client.rewards.ShareAndReferResponse;
import com.tresshop.engine.services.ShareAndReferService;
import com.tresshop.engine.services.mapper.ShareAndReferMapper;
import com.tresshop.engine.storage.entity.ShareAndReferEntity;
import com.tresshop.engine.storage.repository.ShareAndReferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShareAndReferServiceImpl implements ShareAndReferService {

    @Autowired
    private ShareAndReferMapper shareAndReferMapper;

    @Autowired
    private ShareAndReferRepository shareAndReferRepository;

    @Override
    public ShareAndReferResponse shareAndRefer(ShareAndReferRequest shareAndReferRequest) {
        ShareAndReferEntity shareAndReferEntity =
                shareAndReferMapper.populateShareAndReferEntity(shareAndReferRequest);
        shareAndReferRepository.save(shareAndReferEntity);

        return null;
    }

    @Override
    public ShareAndReferResponse refer(ShareAndReferRequest shareAndReferRequest) {
        return null;
    }
}
