package com.tresshop.engine.base.validator;

import com.tresshop.engine.client.rewards.ShareAndReferRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ShareAndReferValidator {

    public boolean validateShareAndReferRequest(ShareAndReferRequest shareAndReferRequest) {
        boolean isValidRequest = true;
        return isValidRequest;
    }
}
