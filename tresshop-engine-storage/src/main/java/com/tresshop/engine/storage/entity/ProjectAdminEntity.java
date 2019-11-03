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
@Table(name = "project_admin" )
@Getter
@Setter
@NoArgsConstructor
public class ProjectAdminEntity implements Serializable {
	@Id
	@Column(name = "admin_id", nullable = false, unique = true)
	private String adminId;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "max_sharing_point")
	private int maxSharingPoint;

	@Column(name = "encash_points")
	private int encashPoints;

	@Column(name = "total_venders")
	private int totalVenders;

	@Column(name = "total_customers")
	private int totalCustomers;

	@Column(name = "creation_date", nullable = false)
	private Timestamp creationDate;

	@Column(name = "updation_date", nullable = false)
	private Timestamp updationDate;


}