package com.tresmoto.batch.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public enum TransactionStatus {
    SUCCESS("SUCCESS"),
    PENDING("PENDING"),
    FAILED("FAILED"),
    ABORTED("ABORTED"),
    INVALID("INVALID"),
    ROLLBACK("ROLLBACK");

    String code;
}
