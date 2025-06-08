package com.jijy.music.configuration.security;

import com.jijy.music.configuration.security.exceptions.CustomAccessDeniedHandler;
import com.jijy.music.configuration.security.exceptions.CustomAuthenticationEntryPoint;
import com.jijy.music.configuration.security.filters.SecurityFilter;
import com.jijy.music.configuration.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.jijy.music.configuration.security.oauth.OAuth2FailureHandler;
import com.jijy.music.configuration.security.oauth.OauthSuccessHandler;
import com.jijy.music.configuration.security.service.CustomOAuth2UserService;
import com.jijy.music.services.implementation.AuthServiceImp;
import com.jijy.music.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtils jwtUtils;

    private final CustomOAuth2UserService customOAuth2UserService;

    private final OauthSuccessHandler oauthSuccessHandler;

    private final HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository;


    private final OAuth2FailureHandler oauthFailureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/v1/auth/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET,"/api/v1/music").permitAll();
                    auth.requestMatchers(HttpMethod.GET,"/api/v1/reproduction-list").permitAll();
//                    auth.requestMatchers("/oauth2/**", "/login/oauth2/**").permitAll();


                    auth.requestMatchers(HttpMethod.GET, "/api/v1/user").hasRole("ADMIN");

                    auth.requestMatchers(HttpMethod.POST, "/api/v1/music").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.PUT, "/api/v1/music").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/api/v1/music").hasRole("ADMIN");

                    auth.anyRequest().authenticated();
                })
//                .oauth2Login(oauth2 -> {
//                    oauth2.authorizationEndpoint(authorizationEndpointConfig -> authorizationEndpointConfig
//                            .baseUri("/oauth2/authorize")
//                            .authorizationRequestRepository(cookieAuthorizationRequestRepository)
//                    );
//
//                    oauth2.redirectionEndpoint(redirectionEndpointConfig -> {
//                        redirectionEndpointConfig.baseUri("/oauth2/callback/*");
//                    });
//
//                    oauth2.userInfoEndpoint(userInfoEndpointConfig -> {
//                        userInfoEndpointConfig.userService(customOAuth2UserService);
//                    });
//                    oauth2.successHandler(oauthSuccessHandler);
//                    oauth2.failureHandler(oauthFailureHandler);
//                })
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .exceptionHandling(exception -> {
                    exception.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
                    exception.accessDeniedHandler(new CustomAccessDeniedHandler());
                })
                .addFilterBefore(new CorsFilter(corsConfigurationSource()), ChannelProcessingFilter.class)
                .addFilterBefore(new SecurityFilter(jwtUtils), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:5173/");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthServiceImp authService) throws Exception {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(authService);

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
