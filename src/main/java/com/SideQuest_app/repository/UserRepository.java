package com.SideQuest_app.repository;

import com.SideQuest_app.domain.model.core.AppUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, String> {
    Optional<AppUser> findByEmail(String username);
    boolean existsByEmail(String username);
    void deleteByEmail(String username);
}
