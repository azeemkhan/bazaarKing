DROP TABLE IF EXISTS system_properties;
CREATE TABLE system_properties (
	property_name VARCHAR(100) NOT NULL,
	property_value VARCHAR(100) NOT NULL,
PRIMARY KEY( property_name )
);
