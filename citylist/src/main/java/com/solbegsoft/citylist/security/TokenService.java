package com.solbegsoft.citylist.security;

/**
 * Token service
 */
public interface TokenService {

    /**
     * Create token from username
     *
     * @param userName user name
     * @return token
     */
    String createToken(String userName);

    /**
     * Validate token and return user name, can return null when token invalid
     *
     * @param token token
     * @return user name
     */
    String validateTokenAndGetUsername(String token);

    String createBearer(String rawToken);
}
