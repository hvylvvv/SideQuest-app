package com.SideQuest_app.service.core.user;

import com.SideQuest_app.domain.model.core.AppUser;
import com.SideQuest_app.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<AppUser> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public AppUser save(AppUser colabUser) {
        return userRepository.save(colabUser);
    }

}
