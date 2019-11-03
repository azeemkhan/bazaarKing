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
@Table(name = "share_and_refer" )
@IdClass(ShareAndReferEntity.CompositeKeys.class)
@Getter
@Setter
@NoArgsConstructor
public class ShareAndReferEntity implements Serializable {
	@Id
	@Column(name = "from_user", nullable = false, unique = true)
	private String fromUser;

	@Id
	@Column(name = "to_user", nullable = false, unique = true)
	private String toUser;

	@Column(name = "type", nullable = false)
	private String type;

	@Column(name = "platform_to_share", nullable = false)
	private String platformToShare;

	@Id
	@Column(name = "code", nullable = false, unique = true)
	private String code;

	@Column(name = "points")
	private int points;

	@Column(name = "created_ts", nullable = false)
	private Timestamp createdTs;

	@Column(name = "last_Updated_ts", nullable = false)
	private Timestamp lastUpdatedTs;

	//In Case of composite keys we have to create a IdClass
    static class CompositeKeys implements Serializable {
		private String fromUser;
		private String toUser;
		private String code;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CompositeKeys ids = (CompositeKeys) o;
			return Objects.equals(fromUser, ids.fromUser) && Objects.equals(toUser, ids.toUser) && Objects.equals(code, ids.code);
		}
		@Override
		public int hashCode() { return Objects.hash(fromUser, toUser, code); }
}

}