DROP TABLE comments_and_reviews IF EXISTS; 
CREATE TABLE comments_and_reviews (
	product_id VARCHAR(5000),
	vendor_id VARCHAR(5000) NOT NULL,
	cust_id VARCHAR(1000) NOT NULL,
	liked BOOLEAN,
	comments VARCHAR(10000),
	watch_later VARCHAR(10000),
	creation_date TIMESTAMP NOT NULL,
	updatation_date TIMESTAMP NOT NULL
);
