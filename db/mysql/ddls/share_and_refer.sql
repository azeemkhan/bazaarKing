DROP TABLE IF EXISTS share_and_refer; 
CREATE TABLE share_and_refer (
	from_user VARCHAR(100) NOT NULL,
	to_user VARCHAR(100),
	type VARCHAR(50) NOT NULL,
	platform_to_share VARCHAR(500) NOT NULL,
	code VARCHAR(50) NOT NULL,
	points INT,
	created_ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	last_Updated_ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
PRIMARY KEY( from_user, to_user, code )
);
