DROP TABLE IF EXISTS archive_table;  
CREATE TABLE archive_table (
	arch_id VARCHAR(100) NOT NULL,
	type VARCHAR(100) NOT NULL,
	history BLOB,
	creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        updation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
PRIMARY KEY( arch_id )
);