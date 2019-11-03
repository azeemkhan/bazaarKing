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
@Table(name = "product_category" )
@IdClass(ProductCategoryEntity.CompositeKeys.class)
@Getter
@Setter
@NoArgsConstructor
public class ProductCategoryEntity implements Serializable {
	@Id
	@Column(name = "vendor_type", nullable = false, unique = true)
	private String vendorType;

	@Id
	@Column(name = "category_name", nullable = false, unique = true)
	private String categoryName;

	@Column(name = "possible_size", nullable = false)
	private String possibleSize;

	@Column(name = "possible_color", nullable = false)
	private String possibleColor;

	@Column(name = "possible_type", nullable = false)
	private String possibleType;

	@Column(name = "possible_quality", nullable = false)
	private String possibleQuality;

	@Column(name = "creation_date", nullable = false)
	private Timestamp creationDate;

	@Column(name = "updation_date", nullable = false)
	private Timestamp updationDate;

	//In Case of composite keys we have to create a IdClass
    static class CompositeKeys implements Serializable {
		private String vendorType;
		private String categoryName;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CompositeKeys ids = (CompositeKeys) o;
			return Objects.equals(vendorType, ids.vendorType) && Objects.equals(categoryName, ids.categoryName);
		}
		@Override
		public int hashCode() { return Objects.hash(vendorType, categoryName); }
}

}