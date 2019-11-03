DROP TABLE IF EXISTS product_category;
CREATE TABLE  product_category (
    vendor_type VARCHAR(100) NOT NULL,
	category_name VARCHAR(100) NOT NULL,
	possible_size VARCHAR(500) NOT NULL,
	possible_color VARCHAR(500) NOT NULL,
	possible_type VARCHAR(500) NOT NULL,
	possible_quality VARCHAR(500) NOT NULL,
	creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
PRIMARY KEY( vendor_type, category_name )
);
