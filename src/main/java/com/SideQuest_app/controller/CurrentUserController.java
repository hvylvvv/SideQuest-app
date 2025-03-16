package com.SideQuest_app.controller;

import com.SideQuest_app.controller.auth.PermissionsService;
import com.SideQuest_app.controller.dto.UserModel;
import com.SideQuest_app.service.core.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CurrentUserController {
    private final PermissionsService permissionsService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/api/me")
    public UserModel.UserView getCurrentUser() {
        var currentUser = permissionsService.getCurrentUser();
        var userInfo = userService.getByEmail(currentUser.getEmail());
        return modelMapper.map(userInfo, UserModel.UserView.class);
    }

}
