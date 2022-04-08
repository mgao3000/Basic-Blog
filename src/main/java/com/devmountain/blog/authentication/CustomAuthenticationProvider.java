package com.devmountain.blog.authentication;

import com.devmountain.blog.model.User;
import com.devmountain.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userService.findByUserName(username);
        if (isUsernamePasswordValid(user, username, password)) {
            return new UsernamePasswordAuthenticationToken(
                    username, password, new ArrayList<>());
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    private boolean isUsernamePasswordValid(User user, String username,
                                            String password) {
        boolean isValid = false;
        if(user != null && Objects.equals(user.getUsername(), username)
                    && Objects.equals(user.getPasswordHash(), password))
            isValid = true;
        return isValid;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
