package com.SideQuest_app.repository;

import com.SideQuest_app.domain.model.auth.UserAuthority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthorityRepository extends JpaRepository<UserAuthority, String> {
}
