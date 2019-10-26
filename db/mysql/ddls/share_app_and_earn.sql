DROP TABLE share_app_and_earn IF EXISTS; 
CREATE TABLE share_app_and_earn (
	pusher VARCHAR(1000) NOT NULL,
	pooler VARCHAR(1000),
	share_type VARCHAR(500) NOT NULL,
	code VARCHAR(500) NOT NULL,
	creation_date TIMESTAMP NOT NULL,
	updatation_date TIMESTAMP NOT NULL,
PRIMARY KEY( pusher, pooler, code )
);
