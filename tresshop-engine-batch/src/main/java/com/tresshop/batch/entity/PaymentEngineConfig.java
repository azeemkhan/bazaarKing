package com.tresmoto.batch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Audited
@Table(name = "PAYMENT_ENGINE_CONFIG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEngineConfig extends AbstractAttributes {

    @Id
    @Column(name="PAYMENT_ENGINE_CONFIG_ID", unique=true, nullable = false)

    private Long id;

    @Column(name = "KEY")
    private String key;

    @Column(name = "VALUE")
    private String value;

    @Column(name = "DESCRIPTION")
    private String description;
}
