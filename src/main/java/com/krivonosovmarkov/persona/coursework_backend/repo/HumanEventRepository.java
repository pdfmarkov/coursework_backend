package com.krivonosovmarkov.persona.coursework_backend.repo;

import com.krivonosovmarkov.persona.coursework_backend.module.HumanEvent;
import com.krivonosovmarkov.persona.coursework_backend.module.HumanEventId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HumanEventRepository extends JpaRepository<HumanEvent, HumanEventId> {
}