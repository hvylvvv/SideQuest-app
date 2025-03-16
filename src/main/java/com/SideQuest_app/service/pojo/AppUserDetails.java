package com.SideQuest_app.service.pojo;

import com.SideQuest_app.domain.model.core.auth.LoginProvider;
import lombok.Builder;
import lombok.Data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class AppUserDetails implements UserDetails, OidcUser {

    String firstName;
    String lastName;

    String password;
    String email;
    String userId;
    String accessToken;

    LoginProvider provider;

    Map<String, Object> attributes;
    List<GrantedAuthority> authorities;

    public void addAuthority(GrantedAuthority authority) {
        authorities.add(authority);
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return "%s %s".formatted(firstName, lastName);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getClaims() {
        return null;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return null;
    }

}
