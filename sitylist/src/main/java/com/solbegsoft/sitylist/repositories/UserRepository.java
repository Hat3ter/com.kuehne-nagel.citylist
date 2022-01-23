package com.solbegsoft.sitylist.repositories;

import com.solbegsoft.sitylist.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * User repository
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Find by email
     *
     * @param email email
     * @return {@link User}
     */
    User findByEmail(String email);
}
