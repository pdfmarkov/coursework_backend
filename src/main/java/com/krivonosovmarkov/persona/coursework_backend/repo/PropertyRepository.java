package com.krivonosovmarkov.persona.coursework_backend.repo;

import com.krivonosovmarkov.persona.coursework_backend.module.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PropertyRepository extends JpaRepository<Property, Long> {


    @Query(value = "select is_property_good_for_doctor(:doctorId, :propertyId)", nativeQuery = true)
    Boolean checkProperty(Long doctorId, Long propertyId);

    @Query(value = "select case when o.human_human_id is null then false else true end " +
            "from opinion o where o.property_property_id = :propertyId and o.human_human_id = :doctorId",
            nativeQuery = true)
    Boolean checkPropertyDoctor(Long doctorId, Long propertyId);

    @Modifying(clearAutomatically = true)
    @Query(value = "update Opinion set isGood = :isGood " +
            "where human.humanId = :humanId and property.propertyId = :propertyId")
    void updateProperty(Long humanId, Long propertyId, Boolean isGood);

}