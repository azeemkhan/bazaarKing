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
@Table(name = "offers_tracing" )
@IdClass(OffersTracingEntity.CompositeKeys.class)
@Getter
@Setter
@NoArgsConstructor
public class OffersTracingEntity implements Serializable {
	@Id
	@Column(name = "offer_id", nullable = false, unique = true)
	private String offerId;

	@Id
	@Column(name = "vendor_id", nullable = false, unique = true)
	private String vendorId;

	@Column(name = "product_id", nullable = false)
	private String productId;

	@Column(name = "offer_type", nullable = false)
	private String offerType;

	@Column(name = "reduced_price")
	private double reducedPrice;

	@Column(name = "offer_percentage")
	private double offerPercentage;

	@Column(name = "enable_offer")
	private boolean enableOffer;

	@Column(name = "creation_date", nullable = false)
	private Timestamp creationDate;

	@Column(name = "updation_date", nullable = false)
	private Timestamp updationDate;

	//In Case of composite keys we have to create a IdClass
    static class CompositeKeys implements Serializable {
		private String offerId;
		private String vendorId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CompositeKeys ids = (CompositeKeys) o;
			return Objects.equals(offerId, ids.offerId) && Objects.equals(vendorId, ids.vendorId);
		}
		@Override
		public int hashCode() { return Objects.hash(offerId, vendorId); }
}

}