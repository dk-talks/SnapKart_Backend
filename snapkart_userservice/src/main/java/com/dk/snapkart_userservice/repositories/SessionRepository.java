package com.dk.snapkart_userservice.repositories;

import com.dk.snapkart_userservice.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.LinkOption;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Session save(Session session);
    Session findByUserId(Long userId);
}
