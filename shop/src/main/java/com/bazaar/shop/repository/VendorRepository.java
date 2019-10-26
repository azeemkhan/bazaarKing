package com.bazaar.shop.repository;

import com.bazaar.shop.entity.VendorDocument;
import com.bazaar.shop.models.Sale;

import java.util.List;

public interface VendorRepository {
    VendorDocument createVendor(VendorDocument vendorDocument);
    VendorDocument findVendorByName(String name);
    List<VendorDocument> findNearByVendors(Double latitude, Double longitude);
    void createSale(List<Sale> sales);
}
