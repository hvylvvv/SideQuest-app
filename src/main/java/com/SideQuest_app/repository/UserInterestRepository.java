package com.SideQuest_app.repository;

import com.SideQuest_app.domain.model.core.UserInterest;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInterestRepository extends JpaRepository<UserInterest, Long> {
}
