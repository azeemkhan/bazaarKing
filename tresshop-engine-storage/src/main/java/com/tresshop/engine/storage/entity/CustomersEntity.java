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
@Table(name = "customers" )
@Getter
@Setter
@NoArgsConstructor
public class CustomersEntity implements Serializable {
	@Id
	@Column(name = "customer_id", nullable = false, unique = true)
	private String customerId;

	@Column(name = "user_full_name", nullable = false)
	private String userFullName;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "email_id", nullable = false)
	private String emailId;

	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;

	@Column(name = "countryID", nullable = false)
	private String countryID;

	@Column(name = "regionID", nullable = false)
	private String regionID;

	@Column(name = "profile_image_link")
	private String profileImageLink;

	@Column(name = "default_location_lat")
	private double defaultLocationLat;

	@Column(name = "default_location_long")
	private double defaultLocationLong;

	@Column(name = "creation_date", nullable = false)
	private Timestamp creationDate;

	@Column(name = "updation_date", nullable = false)
	private Timestamp updationDate;


}