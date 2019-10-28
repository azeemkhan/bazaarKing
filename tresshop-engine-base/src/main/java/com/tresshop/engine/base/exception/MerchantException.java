package com.tresshop.engine.base.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MerchantException extends RuntimeException {

    private String code;

    private String msg;

    MerchantException(String code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }
}
