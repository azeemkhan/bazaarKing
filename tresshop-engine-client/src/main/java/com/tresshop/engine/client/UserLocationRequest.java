package com.tresmoto.engine.client;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLocationRequest {

    private String latitude;

    private String longitude;

    private String searchRadius;
}
