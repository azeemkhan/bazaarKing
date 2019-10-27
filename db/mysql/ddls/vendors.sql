DROP TABLE IF EXISTS vendors;
CREATE TABLE vendors (
        vendor_id VARCHAR(100) NOT NULL,
        vendor_name VARCHAR(500) NOT NULL,
        vendor_address VARCHAR(500) NOT NULL,
        vendor_latitude_longitude POINT NOT NULL,
        vendor_description BLOB,
        vendor_contact_details VARCHAR(500) NOT NULL,
        vendor_type VARCHAR(100),
        vendor_icon_link VARCHAR(500),
        vendor_ratings DECIMAL(2,1),
        vendor_timing VARCHAR(500),
        vendor_delivery_status BOOLEAN,
        vendor_emio_status BOOLEAN,
        creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        updation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
PRIMARY KEY( vendor_id )
);
