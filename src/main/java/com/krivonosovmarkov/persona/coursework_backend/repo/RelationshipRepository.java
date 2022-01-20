package com.krivonosovmarkov.persona.coursework_backend.repo;

import com.krivonosovmarkov.persona.coursework_backend.module.Relationship;
import com.krivonosovmarkov.persona.coursework_backend.module.RelationshipId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationshipRepository extends JpaRepository<Relationship, RelationshipId> {



}