DROP TABLE customers IF EXISTS; 
CREATE TABLE customers (
	cust_id VARCHAR(1000) NOT NULL,
	password VARCHAR(1000) NOT NULL,
	email_id VARCHAR(1000) NOT NULL,
	phone_number BIGINT NOT NULL,
	profile_image VARCHAR(500),
	points INT, 
	subscribed_vendors INT,
	creation_date TIMESTAMP NOT NULL,
	updatation_date TIMESTAMP NOT NULL,
PRIMARY KEY( cust_id )
);
