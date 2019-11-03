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
@Table(name = "archive_table" )
@Getter
@Setter
@NoArgsConstructor
public class ArchiveTableEntity implements Serializable {
	@Id
	@Column(name = "arch_id", nullable = false, unique = true)
	private String archId;

	@Column(name = "type", nullable = false)
	private String type;

	@Column(name = "history")
	private String history;

	@Column(name = "creation_date", nullable = false)
	private Timestamp creationDate;

	@Column(name = "updation_date", nullable = false)
	private Timestamp updationDate;


}