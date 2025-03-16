package com.SideQuest_app.repository;


import com.SideQuest_app.domain.model.core.auth.Authority;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {
    Optional<Authority> findByName(String authority);
}
