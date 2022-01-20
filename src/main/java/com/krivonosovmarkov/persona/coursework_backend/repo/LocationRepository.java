package com.krivonosovmarkov.persona.coursework_backend.repo;

import com.krivonosovmarkov.persona.coursework_backend.module.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

}