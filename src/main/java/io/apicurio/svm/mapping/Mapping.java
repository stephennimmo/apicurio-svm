package io.apicurio.svm.mapping;

import io.apicurio.svm.system.System;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mapping mapping = (Mapping) o;

        if (!Objects.equals(mappingId, mapping.mappingId)) return false;
        if (!Objects.equals(sourceSystem, mapping.sourceSystem))
            return false;
        if (!Objects.equals(sourceValue, mapping.sourceValue)) return false;
        if (!Objects.equals(targetSystem, mapping.targetSystem))
            return false;
        if (!Objects.equals(targetValue, mapping.targetValue)) return false;
        return Objects.equals(targetValueType, mapping.targetValueType);
    }

    @Override
    public int hashCode() {
        int result = mappingId != null ? mappingId.hashCode() : 0;
        result = 31 * result + (sourceSystem != null ? sourceSystem.hashCode() : 0);
        result = 31 * result + (sourceValue != null ? sourceValue.hashCode() : 0);
        result = 31 * result + (targetSystem != null ? targetSystem.hashCode() : 0);
        result = 31 * result + (targetValue != null ? targetValue.hashCode() : 0);
        result = 31 * result + (targetValueType != null ? targetValueType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Mapping{" +
                "mappingId=" + mappingId +
                ", sourceSystem=" + sourceSystem +
                ", sourceValue='" + sourceValue + '\'' +
                ", targetSystem=" + targetSystem +
                ", targetValue='" + targetValue + '\'' +
                ", targetValueType='" + targetValueType + '\'' +
                '}';
    }
}
