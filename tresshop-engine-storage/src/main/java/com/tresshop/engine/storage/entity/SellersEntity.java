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
@Table(name = "sellers" )
@Getter
@Setter
@NoArgsConstructor
public class SellersEntity implements Serializable {
	@Column(name = "user_full_name", nullable = false)
	private String userFullName;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "email_id", nullable = false)
	private String emailId;

	@Id
	@Column(name = "phone_number", nullable = false, unique = true)
	private String phoneNumber;

	@Column(name = "countryID", nullable = false)
	private String countryID;

	@Column(name = "regionID", nullable = false)
	private String regionID;

	@Column(name = "vendor_ids", nullable = false)
	private String vendorIds;

	@Column(name = "status", nullable = false)
	private boolean status;

	@Column(name = "TIN")
	private String TIN;

	@Column(name = "creation_date", nullable = false)
	private Timestamp creationDate;

	@Column(name = "updation_date", nullable = false)
	private Timestamp updationDate;


}