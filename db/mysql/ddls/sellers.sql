DROP TABLE sellers IF EXISTS;
CREATE TABLE sellers (
	seller_id  VARCHAR(1000) NOT NULL,
	password VARCHAR(1000) NOT NULL,
	email_id VARCHAR(1000) NOT NULL,
	phone_number BIGINT NOT NULL,
	vendor_id VARCHAR(5000) NOT NULL,
	TIN VARCHAR(1000),
	points INT,
	creation_date TIMESTAMP NOT NULL,
	updation_date TIMESTAMP NOT NULL,
PRIMARY KEY ( seller_id )
);
