package com.SideQuest_app.service.core.user;

import com.SideQuest_app.domain.model.core.AppUser;

import java.util.Optional;

public interface UserService {
    Optional<AppUser> getByEmail(String email);
    AppUser save(AppUser appUser);
}
