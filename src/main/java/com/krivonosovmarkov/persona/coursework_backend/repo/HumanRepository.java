package com.krivonosovmarkov.persona.coursework_backend.repo;

import com.krivonosovmarkov.persona.coursework_backend.module.Human;
import com.krivonosovmarkov.persona.coursework_backend.module.TypeOfRelations;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface HumanRepository extends JpaRepository<Human, Long> {

    List<Human> findHumansBy(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query(value = "insert into humanevent(human_human_id, event_event_id, is_good) " +
            "values (:humanId, :eventId, :isGood)", nativeQuery = true)
    void insertNewEvent(Long humanId, Long eventId, Boolean isGood);

    @Modifying(clearAutomatically = true)
    @Query(value = "insert into opinion(human_human_id, property_property_id, is_good) " +
            "values (:humanId, :propertyId, :isGood)", nativeQuery = true)
    void insertNewProperty(Long humanId, Long propertyId, Boolean isGood);

    @Modifying(clearAutomatically = true)
    @Query(value = "insert into relationship(human1_human_id, human2_human_id, type, start_date, end_date) " +
            "values (:human1Id, :human2Id, :typeOfRelations, :startDate, :endDate)", nativeQuery = true)
    void insertNewRelationship(Long human1Id, Long human2Id, TypeOfRelations typeOfRelations,
                               LocalDateTime startDate, LocalDateTime endDate);

    List<Human> findHumansByOrderByHumanId(Pageable pageable);

}