DROP TABLE IF EXISTS share_app_and_earn; 
CREATE TABLE share_app_and_earn (
	pusher VARCHAR(500) NOT NULL,
	pooler VARCHAR(500),
	share_type VARCHAR(500) NOT NULL,
	code VARCHAR(10) NOT NULL,
	creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
PRIMARY KEY( pusher, pooler, code )
);
