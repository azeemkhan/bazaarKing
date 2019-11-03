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
import javax.persistence.IdClass;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "likes" )
@IdClass(LikesEntity.CompositeKeys.class)
@Getter
@Setter
@NoArgsConstructor
public class LikesEntity implements Serializable {
	@Id
	@Column(name = "item_liked_id", nullable = false, unique = true)
	private String itemLikedId;

	@Id
	@Column(name = "customer_id", nullable = false, unique = true)
	private String customerId;

	@Column(name = "item_type", nullable = false)
	private String itemType;

	@Column(name = "creation_date", nullable = false)
	private Timestamp creationDate;

	@Column(name = "updation_date", nullable = false)
	private Timestamp updationDate;

	//In Case of composite keys we have to create a IdClass
    static class CompositeKeys implements Serializable {
		private String customerId;
		private String itemLikedId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CompositeKeys ids = (CompositeKeys) o;
			return Objects.equals(customerId, ids.customerId) && Objects.equals(itemLikedId, ids.itemLikedId);
		}
		@Override
		public int hashCode() { return Objects.hash(customerId, itemLikedId); }
}

}