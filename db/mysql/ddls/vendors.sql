DROP TABLE vendors IF EXISTS;
CREATE TABLE vendors (
	vendor_id VARCHAR(5000) NOT NULL,
	vendor_name VARCHAR(1000) NOT NULL,
	vendor_address VARCHAR(5000) NOT NULL, 
	vendor_latitude_longitude POINT NOT NULL,
	vendor_description VARCHAR(8000),
	vendor_contact_details VARCHAR(5000) NOT NULL, 	
	vendor_type VARCHAR(500),
	vendor_icon_link VARCHAR(1000),
	vendor_ratings DECIMAL(2,1),
	vendor_timing VARCHAR(500),
	vendor_delivery_status BOOLEAN,
	vendor_EMI_status BOOLEAN,	
	creation_date TIMESTAMP NOT NULL,
	updatation_date TIMESTAMP NOT NULL,
PRIMARY KEY( vender_id )
);
