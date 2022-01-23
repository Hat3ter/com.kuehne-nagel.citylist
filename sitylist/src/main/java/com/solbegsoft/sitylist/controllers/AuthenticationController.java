package com.solbegsoft.sitylist.controllers;

import com.solbegsoft.sitylist.models.reponse.ResponseApi;
import com.solbegsoft.sitylist.models.requests.AuthRequest;
import com.solbegsoft.sitylist.security.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

/**
 * Auth controller
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    /**
     * @see UserDetailsService
     */
    private final UserDetailsService userDetailsService;

    /**
     * @see UserDetailsService
     */
    private final AuthenticationManager authenticationManager;

    /**
     * @see UserDetailsService
     */
    private final JwtTokenService tokenService;

    /**
     * Get token
     *
     * @param request {@link AuthRequest}
     * @return {@link ResponseApi}
     */
    @PostMapping
    public ResponseApi<String> getToken(@RequestBody AuthRequest request) {

        var authentication = new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword());
        authenticationManager.authenticate(authentication);

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getLogin());
        final String token = tokenService.createToken(userDetails.getUsername());

        return new ResponseApi<>(tokenService.createBearer(token));
    }
}
