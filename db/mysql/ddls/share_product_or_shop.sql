DROP TABLE share_product_or_shop IF EXISTS; 
CREATE TABLE share_product_or_shop (
	vendor_id VARCHAR(5000) NOT NULL,
	product_id VARCHAR(5000),
	platform_to_share VARCHAR(500) NOT NULL,
	cust_id VARCHAR(1000) NOT NULL,
	share_to_whom VARCHAR(1000) NOT NULL,
	share_type VARCHAR(500) NOT NULL,
	earned_points INT, 
	creation_date TIMESTAMP NOT NULL,
	updatation_date TIMESTAMP NOT NULL,
PRIMARY KEY( vendor_id, product_id, cust_id, share_to_whom, share_type )
);
