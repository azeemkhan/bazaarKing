package com.tresshop.engine.storage.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "system_properties")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SystemProperty {

    @Id
    @Column(name = "property_name", nullable = false, unique = true)
    private String propertyName;

    @Column(name = "property_value", nullable = false)
    private String propertyValue;
}
