DROP TABLE project_admin IF EXISTS; 
CREATE TABLE project_admin (
	admin_id VARCHAR(1000) NOT NULL,
	password VARCHAR(1000) NOT NULL,
	max_sharing_point INT,
	encash_points INT, 
	total_venders BIGINT,
	total_customers BIGINT,
PRIMARY KEY( admin_id )
);
