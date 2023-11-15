package io.apicurio.svm.mapping;

import io.apicurio.svm.system.System;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Mapping extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mapping_id")
    public Integer mappingId;

    @ManyToOne
    @JoinColumn(name = "source_system_id")
    public System sourceSystem;

    @Column(name = "source_value")
    @NotEmpty
    public String sourceValue;

    @ManyToOne
    @JoinColumn(name = "target_system_id")
    public System targetSystem;

    @Column(name = "target_value")
    @NotEmpty
    public String targetValue;

    @Column(name = "target_value_type")
    @NotEmpty
    public String targetValueType;

}
