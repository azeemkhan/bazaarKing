package com.tresmoto.engine.services.shop.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tresmoto.engine.base.utils.UUIDUtils;
import com.tresmoto.engine.client.shop.ShopLocationDetails;
import com.tresmoto.engine.services.shop.ShopService;
import com.tresmoto.engine.storage.entity.MerchantLocationEntity;
import com.tresmoto.engine.storage.repository.MerchantLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mysql.cj.xdevapi.*;

import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private MerchantLocationRepository merchantLocationRepository;

    @Autowired
    private UUIDUtils uuidUtils;

    public String onBoardShop(ShopLocationDetails shopLocationDetails) {

        try {
            // Connecting to MySQL and working with a Session
// Connect to a dedicated MySQL server using a connection URI
            Session mySession = new SessionFactory().getSession("mysqlx://localhost:33060/tresshop?user=root&password=abhi@Nov11");

// Get a list of all available schemas
            Schema schema = mySession.getSchema("tresshop");
            // Use the collection 'my_collection'
            // schema.createCollection("my_collection");
            Collection myColl = schema.getCollection("my_collection");

             myColl.add("{\"_id\":\"123\",\"a\":1}");
           // System.out.println("User Provided Id:" + ((JsonString) myColl.getOne("123")).getString());


           //  myColl.add("{\"b\":2}").execute();
           // System.out.println("Autogenerated Id:" + result.getGeneratedIds().get(0));

            mySession.close();
        } catch (Exception exp) {
            System.out.println("Exception");
        }

        MerchantLocationEntity merchantLocationEntity = new MerchantLocationEntity();
        merchantLocationEntity.setShopId(uuidUtils.createUniqueIdForMerchant());
        merchantLocationEntity.setShopName(shopLocationDetails.getShopName());
        merchantLocationEntity.setShopLatitude(shopLocationDetails.getLatitude());
        merchantLocationEntity.setShopLongitude(shopLocationDetails.getLongitude());
        merchantLocationEntity.setShopName(shopLocationDetails.getShopName());
        merchantLocationEntity.setShopDescription(shopLocationDetails.getShopDescription());
        merchantLocationRepository.save(merchantLocationEntity);
        return merchantLocationEntity.getShopId();
    }
}