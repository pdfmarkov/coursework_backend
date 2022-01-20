package com.krivonosovmarkov.persona.coursework_backend.repo;

import com.krivonosovmarkov.persona.coursework_backend.module.RefreshToken;
import com.krivonosovmarkov.persona.coursework_backend.module.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByUser(User user);
}