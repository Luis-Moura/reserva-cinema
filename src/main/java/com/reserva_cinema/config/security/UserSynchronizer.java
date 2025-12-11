package com.reserva_cinema.config.security;

import com.reserva_cinema.domain.entity.UserEntity;
import com.reserva_cinema.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class UserSynchronizer implements ApplicationListener<AuthenticationSuccessEvent> {

    private final UserRepository userRepository;

    public UserSynchronizer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        if (event.getAuthentication() instanceof JwtAuthenticationToken token) {
            Jwt jwt = token.getToken();

            String userId = jwt.getSubject();
            String email = jwt.getClaimAsString("email");
            String name = jwt.getClaimAsString("name");

            userRepository.findById(userId).ifPresentOrElse(
                    existingUser -> {
                        boolean changed = false;

                        if (!existingUser.getEmail().equals(email)) {
                            existingUser.setEmail(email);
                            changed = true;
                        }

                        if (!existingUser.getName().equals(name)) {
                            existingUser.setName(name);
                            changed = true;
                        }

                        if (changed) {
                            userRepository.save(existingUser);
                        }
                    },
                    () -> {
                        UserEntity newUser = new UserEntity();
                        newUser.setId(userId);
                        newUser.setEmail(email);
                        newUser.setName(name);

                        userRepository.save(newUser);
                    }
            );
        }
    }
}
