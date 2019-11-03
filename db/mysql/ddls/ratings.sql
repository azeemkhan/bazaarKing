DROP TABLE IF EXISTS ratings;
CREATE TABLE ratings (
	item_rating_on_id VARCHAR(500) NOT NULL,
	customer_id VARCHAR(100) NOT NULL,
	item_type VARCHAR(50) NOT NULL,
	ratings DECIMAL(2,1),
	creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
PRIMARY KEY ( customer_id )
);