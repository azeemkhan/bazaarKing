package com.tresmoto.batch.constants;

import lombok.ToString;

@ToString
public enum PaymentGatewayType {

    RAZORPAY("RAZORPAY"),
    BILLDESK("BILLDESK"),
    PAYTM("PAYTM"),
    COD_GATEWAY("COD_GATEWAY"),
    INTERNAL_WALLET_GATEWAY("INTERNAL_WALLET_GATEWAY");

    String code;

    String value;

    PaymentGatewayType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
