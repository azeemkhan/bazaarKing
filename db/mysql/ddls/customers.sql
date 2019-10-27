DROP TABLE IF EXISTS customers; 
CREATE TABLE customers (
	cust_id VARCHAR(100) NOT NULL,
	password VARCHAR(500) NOT NULL,
	email_id VARCHAR(500) NOT NULL,
	phone_number BIGINT NOT NULL,
	profile_image VARCHAR(500),
	points INT, 
	subscribed_vendors INT,
	creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
PRIMARY KEY( cust_id )
);
