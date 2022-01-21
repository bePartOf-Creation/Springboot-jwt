package com.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static com.demo.security.SecurityConstants.*;
import static java.util.Arrays.stream;

@Configuration
@Component
@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Allow if path is equal to "/api/user/login'
        if (request.getServletPath().equals("/api/user/login")) {
            filterChain.doFilter(request,response);
        }
        String authorizationHeader = request.getHeader(HEADER_STRING);
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            try{
                //Retrieve access token form the Header
                String token = authorizationHeader.substring(TOKEN_PREFIX.length());
                // Our Signature
                Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes());

                //Verifying our signature(Algorithm)
                JWTVerifier verifier = JWT.require(algorithm).build();

                // Decoding the token , to retrieve the logged-in user(username,password and Its roles for authorization)
                DecodedJWT decodedJWT = verifier.verify(token);
                String username = decodedJWT.getSignature();
                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

                // Granting authority to user role
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                stream(roles).forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority(role));
                });

                //
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username,null,authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request,response);
            }catch (Exception exception) {
                log.error("Error logging in : {}", exception.getMessage());
                response.setHeader("error",exception.getMessage());
            }
        } else {
                filterChain.doFilter(request,response);
        }
    }
}
