package com.krivonosovmarkov.persona.coursework_backend.repo;

import com.krivonosovmarkov.persona.coursework_backend.module.Shadow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShadowRepository extends JpaRepository<Shadow, Long> {

    Optional<Shadow> findByHumanHumanId(Long humanId);

}