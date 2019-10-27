DROP TABLE IF EXISTS share_product_or_shop; 
CREATE TABLE share_product_or_shop (
	vendor_id VARCHAR(100) NOT NULL,
	product_id VARCHAR(500),
	platform_to_share VARCHAR(500) NOT NULL,
	cust_id VARCHAR(100) NOT NULL,
	share_to_whom VARCHAR(50) NOT NULL,
	share_type VARCHAR(20) NOT NULL,
	earned_points INT, 
	creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
PRIMARY KEY( vendor_id, product_id, cust_id, share_to_whom, share_type )
);
