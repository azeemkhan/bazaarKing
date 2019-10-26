DROP TABLE offers IF EXISTS;
CREATE TABLE offers (
	offer_id VARCHAR(1000) NOT NULL,
	vendor_id VARCHAR(5000) NOT NULL,
	product_id VARCHAR(5000) NOT NULL,
	offer_type VARCHAR(500) NOT NULL,
	reduced_price DECIMAL(10,2),
	offer_percentage DECIMAL(10,2),
	offer_logo VARCHAR(8000),
	offer_description VARCHAR(8000),
	enable_offer BOOLEAN,
	offer_duration TIMESTAMP,
	points_on_sharing INT,
	creation_date TIMESTAMP NOT NULL,
	updatation_date TIMESTAMP NOT NULL,
PRIMARY KEY( offer_id ,  vendor_id )
);
