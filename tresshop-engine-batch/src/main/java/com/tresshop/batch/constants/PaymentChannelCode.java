package com.tresmoto.batch.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentChannelCode {
    WEB("WEB"),
    MSITE("MSITE"),
    APP("APP"),
    KIOSK("KIOSK"),
    COCK_PIT("COCK_PIT");

    String code;
}
