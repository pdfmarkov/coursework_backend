package com.krivonosovmarkov.persona.coursework_backend.repo;

import com.krivonosovmarkov.persona.coursework_backend.module.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long> {
}