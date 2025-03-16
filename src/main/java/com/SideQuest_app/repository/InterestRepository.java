package com.SideQuest_app.repository;

import com.SideQuest_app.domain.model.core.Interest;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestRepository extends JpaRepository<Interest, String> {
}
