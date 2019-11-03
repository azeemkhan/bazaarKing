/**----------------------------------------
* GENERATED CODE
* TIME:2019-11-03
* -----------------------------------------
*/
package com.tresshop.engine.storage.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Timestamp;
@Entity
@Table(name = "ratings" )
@Getter
@Setter
@NoArgsConstructor
public class RatingsEntity implements Serializable {
	@Column(name = "item_rating_on_id", nullable = false)
	private String itemRatingOnId;

	@Id
	@Column(name = "customer_id", nullable = false, unique = true)
	private String customerId;

	@Column(name = "item_type", nullable = false)
	private String itemType;

	@Column(name = "ratings")
	private double ratings;

	@Column(name = "creation_date", nullable = false)
	private Timestamp creationDate;

	@Column(name = "updation_date", nullable = false)
	private Timestamp updationDate;


}