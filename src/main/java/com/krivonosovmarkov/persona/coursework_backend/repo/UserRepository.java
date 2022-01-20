package com.krivonosovmarkov.persona.coursework_backend.repo;

import com.krivonosovmarkov.persona.coursework_backend.module.Human;
import com.krivonosovmarkov.persona.coursework_backend.module.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("SELECT u.human.humanId FROM User u WHERE u.username = :username")
    Optional<Long> getHumanIdByUsername(String username);

    Optional<User> findById(Long id);

    Boolean existsByUsername(String username);

}
