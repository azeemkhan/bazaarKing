package com.tresshop.engine.base.utils;

import com.fasterxml.uuid.Generators;
import org.springframework.stereotype.Component;


@Component
public class UUIDUtils {

    public String createUniqueIdForMerchant() {
        return String.valueOf(Generators.timeBasedGenerator().generate());

    }


}
