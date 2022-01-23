package com.solbegsoft.sitylist.services;

import com.solbegsoft.sitylist.AbstractTestCaseTest;
import com.solbegsoft.sitylist.models.entities.User;
import com.solbegsoft.sitylist.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test for {@link UserDetailsDtoService}
 */
class UserDetailsDtoServiceTest extends AbstractTestCaseTest {

    /**
     * @see UserDetailsService
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * @see UserRepository
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Test {@link UserDetailsService#loadUserByUsername(String)}
     */
    @Test
    public void testLoadByUserName() {

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("name"));

        User user = new User();
        user.setRole("role");
        user.setPassword("pass");
        user.setEmail("name");
        user.setId(UUID.randomUUID());
        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername("name");

        assertEquals(user.getEmail(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertEquals(Collections.singletonList(new SimpleGrantedAuthority(user.getRole())), userDetails.getAuthorities());

    }
}