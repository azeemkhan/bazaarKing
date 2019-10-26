package com.bazaar.shop.controller;

import com.bazaar.shop.entity.VendorDocument;
import com.bazaar.shop.models.Vendor;
import com.bazaar.shop.service.vendorService.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/vendor")
public class VendorController {

    @Autowired
    VendorService vendorService;

    @PostMapping
    public ResponseEntity createVendor(@RequestBody Vendor vendor) {
        try {
            vendorService.createVendor(vendor);
        } catch (Exception ex) {
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getVendorDetailsByName(@RequestParam(value = "name") String vendorName) {
        VendorDocument vendorDetails;
        try {
            vendorDetails = vendorService.findVendorByName(vendorName);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(vendorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping
    public ResponseEntity getNearByVendors(
            @RequestParam(value = "lat") String latitude,
            @RequestParam(value = "lot") String longitude) {
        List<Vendor> vendorDetails;
        try {
            vendorDetails = vendorService.findNearByVendors(latitude, longitude);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(vendorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
