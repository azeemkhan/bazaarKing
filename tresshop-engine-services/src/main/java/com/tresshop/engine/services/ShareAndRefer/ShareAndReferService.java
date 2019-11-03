package com.tresshop.engine.services.ShareAndRefer;

import com.tresshop.engine.client.rewards.ShareAndReferRequest;
import com.tresshop.engine.client.rewards.ShareAndReferResponse;

public interface ShareAndReferService {
    ShareAndReferResponse share(ShareAndReferRequest shareAndReferRequest);
    ShareAndReferResponse refer(ShareAndReferRequest shareAndReferRequest);
}
