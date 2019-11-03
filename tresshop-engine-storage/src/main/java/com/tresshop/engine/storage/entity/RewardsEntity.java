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
@Table(name = "rewards" )
@Getter
@Setter
@NoArgsConstructor
public class RewardsEntity implements Serializable {
	@Id
	@Column(name = "reward_id", nullable = false, unique = true)
	private String rewardId;

	@Column(name = "status", nullable = false)
	private String status;

	@Column(name = "customer_id", nullable = false)
	private String customerId;

	@Column(name = "type", nullable = false)
	private String type;

	@Column(name = "description")
	private String description;

	@Column(name = "created_ts", nullable = false)
	private Timestamp createdTs;

	@Column(name = "last_Updated_ts", nullable = false)
	private Timestamp lastUpdatedTs;


}