DROP TABLE products IF EXISTS;
CREATE TABLE  products (
	product_id VARCHAR(5000) NOT NULL,
	vendor_id VARCHAR(5000) NOT NULL,
	price DECIMAL(10,2),
	category_name VARCHAR(500),
	size_available VARCHAR(1000),
	colour_available VARCHAR(1000) ,
	product_type VARCHAR(1000),
	product_quality VARCHAR(1000),
	product_description VARCHAR(8000),
	product_images VARCHAR(8000) NOT NULL,
	product_ratings DECIMAL(2,1),
	total_likes INT,
	total_comments INT,
	out_of_stock BOOLEAN,
	creation_date TIMESTAMP NOT NULL,
	updatation_date TIMESTAMP NOT NULL,
PRIMARY KEY( product_id, vendor_id )
);
