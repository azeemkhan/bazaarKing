package com.tresmoto.batch.provider;

import com.tresmoto.batch.model.EventRequest;

public interface EventHandlerProvider {

    void exchangeData(EventRequest request);
}
