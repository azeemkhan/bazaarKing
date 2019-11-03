DROP TABLE IF EXISTS comments;
CREATE TABLE comments (
	cr_id MEDIUMINT NOT NULL AUTO_INCREMENT,
	item_comments_on_id VARCHAR(500) NOT NULL,
	item_type VARCHAR(50) NOT NULL,
	customer_id VARCHAR(100) NOT NULL,
	comment_quality INT,
	comments BLOB,
	creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
PRIMARY KEY (cr_id)
);
