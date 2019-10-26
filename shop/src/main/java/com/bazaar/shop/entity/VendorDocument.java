package com.bazaar.shop.entity;


import com.bazaar.shop.models.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@ToString
@Document("vendor_document")
public class VendorDocument {
    private String name;
    private String description;
    private List<Category> category = null;
    private String ownerName;
    private String userName;
    private String password;
    private String shopLogo;
    private List<String> photoUrls = null;
    private List<Address> address = null;
    private GeoJsonPoint location = null;
    private Timing timings;
    private List<Sale> sales = null;
    private List<Product> products = null;
}
