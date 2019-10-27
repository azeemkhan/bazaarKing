DROP TABLE IF EXISTS comments_and_reviews; 
CREATE TABLE comments_and_reviews (
	product_id VARCHAR(500),
	vendor_id VARCHAR(100) NOT NULL,
	cust_id VARCHAR(100) NOT NULL,
	liked BOOLEAN,
	comments BLOB,
	watch_later BLOB,
	creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
