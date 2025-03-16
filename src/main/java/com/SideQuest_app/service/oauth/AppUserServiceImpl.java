package com.SideQuest_app.service.oauth;

import com.SideQuest_app.domain.config.GeneralSettings;
import com.SideQuest_app.domain.model.core.AppUser;
import com.SideQuest_app.domain.model.core.auth.Authority;
import com.SideQuest_app.domain.model.core.auth.LoginProvider;
import com.SideQuest_app.repository.AuthorityRepository;
import com.SideQuest_app.repository.UserAuthorityRepository;
import com.SideQuest_app.repository.UserRepository;
import com.SideQuest_app.service.pojo.AppUserDetails;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.extern.log4j.Log4j2;

import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Service
@Value
@NonFinal
@Log4j2
public class AppUserServiceImpl implements AppUserService {

    DefaultOAuth2UserService oauth2Delegate = new DefaultOAuth2UserService();
    OidcUserService oidcDelegate = new OidcUserService();

    UserRepository userRepository;
    AuthorityRepository authorityRepository;
    UserAuthorityRepository userAuthorityRepository;

    PasswordEncoder passwordEncoder;

    GeneralSettings generalSettings;

    Executor executor = new SimpleAsyncTaskExecutor();

    private LoginProvider getProvider(OAuth2UserRequest userRequest) {
        return LoginProvider.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
    }

    @Transactional
    protected void createUser(AppUserDetails appUserDetails) {
        AppUser appUserEntity = saveUserIfNotExists(appUserDetails);
        if (log.isDebugEnabled())
            log.debug("User: {}", appUserEntity);

        Arrays.stream(generalSettings.getAdmins())
                .filter(appUserDetails.getUsername()::equals)
                .findFirst()
                .ifPresent(a -> appUserDetails.addAuthority(new SimpleGrantedAuthority("admin")));

        List<Authority> authorities = appUserDetails
                .getAuthorities()
                .stream()
                .map(a -> saveAuthorityIfNotExists(a.getAuthority(), appUserDetails.getProvider()))
                .toList();

        appUserEntity.mergeAuthorities(authorities);

        userAuthorityRepository.saveAll(appUserEntity.getUserAuthorities());
    }

    private Authority saveAuthorityIfNotExists(String authority, LoginProvider provider) {
        return authorityRepository
                .findByName(authority)
                .orElseGet(() -> authorityRepository
                        .save(Authority.builder()
                                      .name(authority)
                                      .provider(provider)
                                      .build()
                        )
                );
    }

    private AppUser saveUserIfNotExists(AppUserDetails appUserDetails) {
        Optional<AppUser> optUser = userRepository.findByEmail(appUserDetails.getUsername());
        return optUser.orElseGet(() -> userRepository.save(AppUser.builder()
                                                                   .firstName(appUserDetails.getFirstName())
                                                                   .lastName(appUserDetails.getLastName())
                                                                   .email(appUserDetails.getEmail())
                                                                   .build()
        ));
    }

    @Override
    public void createUser(UserDetails user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateUser(UserDetails user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteUser(String username) {
        if (userExists(username)) {
            userRepository.deleteByEmail(username);
        }
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsByEmail(username);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(username)
                .map(ue -> AppUserDetails
                        .builder()
                        .firstName(ue.getFirstName())
                        .lastName(ue.getLastName())
                        .email(ue.getEmail())
                        .authorities(ue
                                             .getUserAuthorities()
                                             .stream()
                                             .map(a -> (GrantedAuthority) new SimpleGrantedAuthority(a.getAuthority().getName()))
                                             .collect(Collectors.toCollection(ArrayList::new))
                        )
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found!", username)));
    }

    @Transactional
    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> oidcLoginHandler() {
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("user"));

        return userRequest -> {
            log.info("oidcLoginHandler: {}", userRequest);
            LoginProvider provider = getProvider(userRequest);
            OidcUser oidcUser = oidcDelegate.loadUser(userRequest);
            AppUserDetails appUserDetails = AppUserDetails
                    .builder()
                    .provider(provider)
                    .firstName(oidcUser.getAttribute("name"))
                    .lastName(oidcUser.getAttribute("family_name"))
                    .email(oidcUser.getEmail())
                    .userId(oidcUser.getName())
                    .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                    .accessToken(oidcUser.getIdToken().getTokenValue())
                    .attributes(oidcUser.getAttributes())
                    .authorities(authorities)
                    .build();

            createUser(appUserDetails);

            return appUserDetails;
        };
    }

    @Transactional
    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2LoginHandler() {
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("user"));

        return userRequest -> {
            LoginProvider provider = getProvider(userRequest);
            OAuth2User oAuth2User = oauth2Delegate.loadUser(userRequest);
            AppUserDetails appUserDetails = AppUserDetails
                    .builder()
                    .provider(provider)
                    .firstName(oAuth2User.getAttribute("name"))
                    .lastName(oAuth2User.getAttribute("family_name"))
                    .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                    .userId(oAuth2User.getName())
                    .accessToken(userRequest.getAccessToken().getTokenValue())
                    .attributes(oAuth2User.getAttributes())
                    .authorities(authorities)
                    .build();

            log.info("User OAuth Token: {}", userRequest.getAccessToken().getTokenValue());
            createUser(appUserDetails);
            if (log.isDebugEnabled())
                log.debug("User OAuth Token: {}", userRequest.getAccessToken().getTokenValue());

            return appUserDetails;
        };
    }
}
