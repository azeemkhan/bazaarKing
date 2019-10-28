package com.tresmoto.batch.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum TransactionInitiator {
    SELF_INITIATED("SELF_INITIATED"),
    TENANT_INITIATED("TENANT_INITIATED");

    String code;
}
