package com.solbegsoft.citylist.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

/**
 * User entity
 */
@Entity
@Table(name = "users")
// created index on email
@Data
@NoArgsConstructor
public class User {

    /**
     * id
     */
    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    /**
     * email
     */
    @Column(name = "email")
    private String email;

    /**
     * password encrypted
     */
    @Column(name = "password")
    private String password;

    /**
     * role
     */
    @Column(name = "role")
    private String role;
}
