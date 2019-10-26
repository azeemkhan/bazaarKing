package com.bazaar.shop.service.vendorService;

import com.bazaar.shop.entity.VendorDocument;
import com.bazaar.shop.models.Vendor;

import java.util.List;

public interface VendorService {
    void createVendor(Vendor vendor) throws Exception;
    VendorDocument findVendorByName(String name) throws Exception;
    List<Vendor> findNearByVendors(String latitude, String longitude);
}
