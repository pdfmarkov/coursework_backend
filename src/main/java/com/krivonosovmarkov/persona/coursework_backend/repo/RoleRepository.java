package com.krivonosovmarkov.persona.coursework_backend.repo;


import com.krivonosovmarkov.persona.coursework_backend.module.ERole;
import com.krivonosovmarkov.persona.coursework_backend.module.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
