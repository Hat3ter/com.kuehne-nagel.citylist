package com.solbegsoft.citylist.models.requests;

import lombok.Data;

/**
 * Request model for authentication
 */
@Data
public class AuthRequest {

    /**
     * login
     */
    private String login;

    /**
     * password
     */
    private String password;
}
