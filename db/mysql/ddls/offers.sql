DROP TABLE IF EXISTS offers;
CREATE TABLE offers (
	offer_id VARCHAR(500) NOT NULL,
	vendor_id VARCHAR(100) NOT NULL,
	product_id VARCHAR(500) NOT NULL,
	offer_type VARCHAR(500) NOT NULL,
	reduced_price DECIMAL(10,2),
	offer_percentage DECIMAL(10,2),
	offer_logo BLOB,
	offer_description BLOB,
	enable_offer BOOLEAN,
	offer_duration TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	points_on_sharing INT,
	creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
PRIMARY KEY( offer_id ,  vendor_id )
);
