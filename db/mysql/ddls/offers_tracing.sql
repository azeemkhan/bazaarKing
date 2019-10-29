DROP TABLE IF EXISTS offers_tracing;
CREATE TABLE offers_tracing (
	offer_id VARCHAR(500) NOT NULL,
	vendor_id VARCHAR(100) NOT NULL,
	product_id VARCHAR(500) NOT NULL,
	offer_type VARCHAR(500) NOT NULL,
	reduced_price DECIMAL(10,2),
	offer_percentage DECIMAL(10,2),
	enable_offer BOOLEAN,
	creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
PRIMARY KEY( offer_id , vendor_id )
);
