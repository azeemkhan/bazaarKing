package com.tresmoto.batch.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum TransactionType {
    PURCHASE_TRANSACTION("PURCHASE_TRANSACTION"),
    REFUND_TRANSACTION("REFUND_TRANSACTION");

    String code;
}
