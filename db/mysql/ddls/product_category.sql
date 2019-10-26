DROP TABLE product_category IF EXISTS;
CREATE TABLE  product_category (
	category_name VARCHAR(500) NOT NULL,
	possible_size VARCHAR(1000) NOT NULL,
	possible_color VARCHAR(1000) NOT NULL,
	possible_type VARCHAR(1000) NOT NULL,
	possible_quality VARCHAR(1000) NOT NULL,
	creation_date TIMESTAMP NOT NULL,
	updatation_date TIMESTAMP NOT NULL,
PRIMARY KEY( category_name )
);
