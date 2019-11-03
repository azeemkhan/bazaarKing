DROP TABLE IF EXISTS customers; 
CREATE TABLE customers (
	customer_id VARCHAR(100) NOT NULL,
	user_full_name VARCHAR(100) NOT NULL,
	password VARCHAR(500) NOT NULL,
	email_id VARCHAR(500) NOT NULL,
	phone_number VARCHAR(13) NOT NULL,
	countryID VARCHAR(2) NOT NULL,
	regionID VARCHAR(2) NOT NULL,
	profile_image_link VARCHAR(500),
	default_location_lat DOUBLE,
	default_location_long DOUBLE,
	creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
PRIMARY KEY( customer_id )
);
