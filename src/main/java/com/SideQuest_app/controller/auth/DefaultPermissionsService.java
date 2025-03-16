package com.SideQuest_app.controller.auth;

import com.SideQuest_app.service.pojo.AppUserDetails;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultPermissionsService implements PermissionsService {

    @Override
    public AppUserDetails getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (AppUserDetails) principal;
    }

}
