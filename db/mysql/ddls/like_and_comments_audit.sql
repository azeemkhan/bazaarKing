DROP TABLE IF EXISTS like_and_comments_audit; 
CREATE TABLE like_and_comments_audit (
	cr_id MEDIUMINT NOT NULL AUTO_INCREMENT,
	product_id VARCHAR(500),
	vendor_id VARCHAR(100) NOT NULL,
	cust_id VARCHAR(100) NOT NULL,
	liked VARCHAR(1),
	comments BLOB,
	status VARCHAR(10),
	ratings DECIMAL(2,1),
	creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
PRIMARY KEY (cr_id)
);
