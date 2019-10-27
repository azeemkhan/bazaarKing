DROP TABLE IF EXISTS products;
CREATE TABLE  products (
	product_id VARCHAR(500) NOT NULL,
	vendor_id VARCHAR(100) NOT NULL,
	price DECIMAL(10,2),
	category_name VARCHAR(500),
	size_available VARCHAR(500),
	colour_available VARCHAR(500) ,
	product_type VARCHAR(500),
	product_quality VARCHAR(500),
	product_description BLOB,
	product_images BLOB NOT NULL,
	product_ratings DECIMAL(2,1),
	total_likes INT,
	total_comments INT,
	out_of_stock BOOLEAN,
	creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
PRIMARY KEY( product_id, vendor_id )
);
