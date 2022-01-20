package com.krivonosovmarkov.persona.coursework_backend.repo;

import com.krivonosovmarkov.persona.coursework_backend.module.Event;
import com.krivonosovmarkov.persona.coursework_backend.module.responses.CustomEventResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "select is_event_good_for_doctor(:doctorId, :eventId)", nativeQuery = true)
    Boolean checkEvent(Long doctorId, Long eventId);

    @Query(value = "select case when he.human_human_id is null then false else true end " +
            "from HumanEvent he where he.event_event_id = :eventId and he.human_human_id = :doctorId",
            nativeQuery = true)
    Boolean checkEventDoctor(Long doctorId, Long eventId);

    @Query(value = "select get_person_imagine_self_id(:humanId)", nativeQuery = true)
    Integer getImagineSelfHumanId(Long humanId);


    @Modifying(clearAutomatically = true)
    @Query(value = "update HumanEvent set isGood = :isGood " +
            "where human.humanId = :humanId and event.eventId = :eventId")
    void updateEvent(Long humanId, Long eventId, Boolean isGood);



}