package com.unipay.payload;


import com.unipay.enums.UserStatus;
import com.unipay.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final User user;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .flatMap(userRole -> {
                    Role role = userRole.getRole();
                    Stream<GrantedAuthority> roleAuthority = Stream.of(
                            new SimpleGrantedAuthority("ROLE_" + role.getName())
                    );
                    Stream<GrantedAuthority> permissionAuthorities = role.getPermissions().stream()
                            .map(permission ->
                                    new SimpleGrantedAuthority(permission.getName().name())
                            );
                    return Stream.concat(roleAuthority, permissionAuthorities);
                })
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getStatus() != UserStatus.SUSPENDED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus() == UserStatus.ACTIVE;
    }
}

