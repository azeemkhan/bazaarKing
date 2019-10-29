package com.tresshop.engine.services;

import com.tresshop.engine.client.rewards.ShareAndReferRequest;
import com.tresshop.engine.client.rewards.ShareAndReferResponse;

public interface ShareAndReferService {
    ShareAndReferResponse shareAndRefer(ShareAndReferRequest shareAndReferRequest);
    ShareAndReferResponse refer(ShareAndReferRequest shareAndReferRequest);
}
