package com.solbegsoft.sitylist.services;

import com.solbegsoft.sitylist.repositories.UserRepository;
import com.solbegsoft.sitylist.utils.UserConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation {@link UserDetailsService}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsDtoService implements UserDetailsService {

    /**
     * @see UserRepository
     */
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return Optional.ofNullable(userRepository.findByEmail(username))
                .map(UserConverter.INSTANCE::convert)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
