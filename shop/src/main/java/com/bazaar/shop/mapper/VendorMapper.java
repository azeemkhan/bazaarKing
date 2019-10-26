package com.bazaar.shop.mapper;

import com.bazaar.shop.entity.VendorDocument;
import com.bazaar.shop.models.Category;
import com.bazaar.shop.models.Timing;
import com.bazaar.shop.models.Vendor;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class VendorMapper {

    private static String dateTimeFormat = "dd/MM/yyyy HH:mm:ss";
    private static DateTimeFormatter formatter = DateTimeFormat.forPattern(dateTimeFormat);

    public static VendorDocument mapVendorToDocument(Vendor vendor) {
        VendorDocument vendorDocument = new VendorDocument();
        vendorDocument.setName(vendor.getName());
        vendorDocument.setDescription(vendor.getDescription());
        vendorDocument.setCategory(mapCategories(vendor.getCategory()));
        vendorDocument.setOwnerName(vendor.getOwnerName());
        vendorDocument.setUserName(vendor.getUserName());
        vendorDocument.setPassword(vendor.getPassword());
        vendorDocument.setShopLogo(vendor.getShopLogo());
        vendorDocument.setPhotoUrls(vendor.getPhotoUrls());
        vendorDocument.setAddress(vendor.getAddress());
        vendorDocument.setLocation(
                createLocation(
                        Double.valueOf(vendor.getAddress().get(0).getPosition().getLatitude()),
                        Double.valueOf(vendor.getAddress().get(0).getPosition().getLatitude())));
        try {
            vendorDocument.setTimings(createTiming(vendor.getOpenTime(), vendor.getCloseTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return vendorDocument;
    }

    public static GeoJsonPoint createLocation(Double latitude, Double longitude) {
        GeoJsonPoint geoJsonPoint = new GeoJsonPoint(latitude, longitude);
        return geoJsonPoint;
    }

    public static List<Category> mapCategories(List<String> categories) {
        List<Category> categoryList = new ArrayList<>();
        return categoryList;
    }

    public static Timing createTiming(String startTime, String endTime) throws ParseException {
        Timing timing = new Timing();
        timing.setStartTime(formatter.parseDateTime(startTime));
        timing.setEndTime(formatter.parseDateTime(endTime));
        return timing;
    }
}
