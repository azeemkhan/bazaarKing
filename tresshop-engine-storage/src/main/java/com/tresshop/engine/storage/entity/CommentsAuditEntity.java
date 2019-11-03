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
@Table(name = "comments_audit" )
@Getter
@Setter
@NoArgsConstructor
public class CommentsAuditEntity implements Serializable {
	@Id
	@Column(name = "cr_id", nullable = false, unique = true)
	private int crId;

	@Column(name = "item_comments_on_id", nullable = false)
	private String itemCommentsOnId;

	@Column(name = "item_type", nullable = false)
	private String itemType;

	@Column(name = "customer_id", nullable = false)
	private String customerId;

	@Column(name = "comments")
	private String comments;

	@Column(name = "creation_date", nullable = false)
	private Timestamp creationDate;

	@Column(name = "updation_date", nullable = false)
	private Timestamp updationDate;


}