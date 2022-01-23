package com.solbegsoft.sitylist.security;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static com.solbegsoft.sitylist.security.JwtTokenService.BEARER_PREFIX;

/**
 * Filter for auth
 */
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {


    /**
     * Bearer prefix size
     */
    private static final int BEARER_PREFIX_SIZE = BEARER_PREFIX.length();

    /**
     * Auth header
     */
    private static final String TOKEN_HEADER = "Authorization";

    /**
     * @see TokenService
     */
    private final TokenService tokenService;

    /**
     * @see UserDetailsService
     */
    private final UserDetailsService userDetailsService;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {

        String token = request.getHeader(TOKEN_HEADER);

        if (Objects.nonNull(token) && token.startsWith(BEARER_PREFIX)) {
            token = token.substring(BEARER_PREFIX_SIZE);
            String username = tokenService.validateTokenAndGetUsername(token);

            if (Objects.nonNull(username)) {
                UserDetails details = userDetailsService.loadUserByUsername(username);
                var authenticationToken = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
                token = tokenService.createToken(username);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } else {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        response.setHeader(TOKEN_HEADER, tokenService.createBearer(token));
        response.setHeader("Access-Control-Expose-Headers",TOKEN_HEADER);
        filterChain.doFilter(request, response);
    }
}
