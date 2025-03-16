package com.SideQuest_app.controller.auth;

import com.SideQuest_app.service.pojo.AppUserDetails;

public interface PermissionsService {

    // current user
    AppUserDetails getCurrentUser();
}
