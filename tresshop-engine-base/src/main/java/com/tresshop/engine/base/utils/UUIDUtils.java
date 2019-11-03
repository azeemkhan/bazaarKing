package com.tresshop.engine.base.utils;

import com.fasterxml.uuid.Generators;
import com.tresshop.engine.client.customers.CustomerRegistrationRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class UUIDUtils {

    public String createUniqueIdForMerchant() {
        return String.valueOf(Generators.timeBasedGenerator().generate());
    }

    public String createUniqueIdForReward() {
        return String.valueOf(Generators.timeBasedGenerator().generate());
    }

    public static String createVendorId(String countryID, String region, String sellerFirstName){
        String prefix = "vend_" + countryID+ "_" + region + "_" + sellerFirstName;
        String genId = String.valueOf(Generators.timeBasedGenerator().generate());
        return prefix + "_" + genId;
    }
    public static String createCustomerId(String phoneNumber){
         return "cust_" + "_" + phoneNumber ;

    }

    public static void main(String[] args) {

        double a = 636.4556667765D;
        int b = 250;
        BigDecimal decimal = new BigDecimal(a/b);

        System.out.println(decimal.setScale(2, BigDecimal.ROUND_HALF_EVEN));
    }

}
