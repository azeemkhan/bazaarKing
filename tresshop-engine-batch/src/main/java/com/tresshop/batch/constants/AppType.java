package com.tresmoto.batch.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppType {
    IOS("IOS"), ANDROID("ANDROID"), OTHER("OTHER");

    String code;
}
