package com.jijy.music.configuration.security.filters;

import com.jijy.music.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenRequest = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (tokenRequest != null && tokenRequest.startsWith("Bearer ")) {
            String token = tokenRequest.substring(7);
            System.out.println("pasa poor aca");

            try {
                Jws<Claims> authorize = jwtUtils.validateJwtToken(token);
                System.out.println(authorize);

                List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(
                        authorize.getBody().get("authorities").toString()
                );


                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        authorize.getBody().getSubject(),
                        null,
                        authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }


        }
        filterChain.doFilter(request, response);
    }
}
