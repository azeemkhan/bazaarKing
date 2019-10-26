package com.bazaar.shop.service.vendorService.impl;

import com.bazaar.shop.entity.VendorDocument;
import com.bazaar.shop.mapper.VendorMapper;
import com.bazaar.shop.models.Vendor;
import com.bazaar.shop.repository.VendorRepository;
import com.bazaar.shop.service.vendorService.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    VendorRepository vendorRepository;

    @Override
    public void createVendor(Vendor vendor) throws Exception {
        VendorDocument vendorDocument = vendorRepository.createVendor(VendorMapper.mapVendorToDocument(vendor));

        if (vendorDocument == null) {
            throw new Exception("Exception occured while creating vendor document");
        }
    }

    @Override
    public VendorDocument findVendorByName(String name) throws Exception {
        VendorDocument vendorDocument = vendorRepository.findVendorByName(name);

        if (vendorDocument == null) {
            throw new Exception("Vendor Not Found");
        }
        return vendorDocument;
    }

    @Override
    public List<Vendor> findNearByVendors(String latitude, String longitude) {
        List<Vendor> vendors = new ArrayList<>();
        List<VendorDocument> vendorsDocuments =
                vendorRepository.findNearByVendors(
                        Double.valueOf(latitude),
                        Double.valueOf(longitude));
        return vendors;
    }
}
