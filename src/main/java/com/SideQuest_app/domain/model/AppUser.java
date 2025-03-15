package com.SideQuest_app.domain.model;

import com.SideQuest_app.domain.model.auth.Authority;
import com.SideQuest_app.domain.model.auth.LoginProvider;
import com.SideQuest_app.domain.model.auth.UserAuthority;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    @Id
    private int id;
    private String firstName;
    private String lastName;
    private String passwordHash;
    private String nationality;
    private String email;
    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    LoginProvider provider;

    @OneToMany(mappedBy = "appUser", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @JsonManagedReference
    private List<UserAuthority> userAuthorities = new ArrayList<>();

    public void addAuthority(Authority authority) {

        if (userAuthorities.stream().anyMatch(uae -> uae.getAppUser().equals(this) && uae.getAuthority().equals(authority))) {
            return;
        }

        UserAuthority userAuthority = UserAuthority.builder()
                .appUser(this)
                .authority(authority)
                .build();

        this.userAuthorities.add(userAuthority);
        authority.getAssignedTo().add(userAuthority);
    }

    public void removeAuthority(Authority authority) {

        Iterator<UserAuthority> iterator = userAuthorities.iterator();

        while (iterator.hasNext()) {
            UserAuthority next = iterator.next();

            if (next.getAuthority().equals(authority)) {
                iterator.remove();
                authority.getAssignedTo().remove(next);
                next.setAppUser(null);
                next.setAuthority(null);
            }
        }

    }

    public void mergeAuthorities(List<Authority> authorities) {
        var toRemove = this
                .userAuthorities
                .stream()
                .filter(uae -> !authorities.contains(uae.getAuthority()))
                .toList();

        toRemove.forEach(uae -> this.removeAuthority(uae.getAuthority()));

        authorities.forEach(this::addAuthority);
    }

}