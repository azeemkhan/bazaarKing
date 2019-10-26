package com.bazaar.shop.repository.impl;

import com.bazaar.shop.entity.VendorDocument;
import com.bazaar.shop.repository.VendorRepository;
import com.mongodb.client.model.geojson.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class VendorRepositoryImpl implements VendorRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public VendorDocument createVendor(VendorDocument vendorDocument) {
        return mongoTemplate.save(vendorDocument);
    }

    @Override
    public VendorDocument findVendorByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        List<VendorDocument> vendorDocuments = mongoTemplate.find(query, VendorDocument.class);
        if (vendorDocuments.isEmpty()) {
            return null;
        } else {
            return vendorDocuments.get(0);
        }
    }

    @Override
    public List<VendorDocument> findNearByVendors(Double latitude, Double longitude) {
        List<VendorDocument> vendorDocuments = new ArrayList<>();
        return vendorDocuments;
    }
}
