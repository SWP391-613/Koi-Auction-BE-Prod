package com.swp391.koibe.configs;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import com.swp391.koibe.filters.JwtTokenFilter;
import com.swp391.koibe.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
@EnableWebMvc
public class WebSecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;

    @Value("${api.prefix}")
    private String apiPrefix;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
        throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(requests -> {
                requests
                    .requestMatchers(
                        String.format("%s/users/register", apiPrefix),
                        String.format("%s/users/login", apiPrefix)
                        // demo api
                        // String.format("%s/products/test/view", apiPrefix)

                        // waiting to enable to add the swagger

                    )
                    .permitAll()

                    .requestMatchers(GET,
                                     String.format("%s/roles**", apiPrefix))
                    .permitAll()

                    .requestMatchers(POST,
                                     String.format("%s/autho2/**", apiPrefix))
                    .permitAll()

                    .requestMatchers(GET,
                                     String.format("%s/categories**", apiPrefix))
                    .permitAll()

                    .requestMatchers(POST,
                                     String.format("%s/categories/**", apiPrefix))
                    .hasAnyRole(Role.MANAGER)

                    .requestMatchers(GET, String.format("%s/auction-websocket/**", apiPrefix))
                    .permitAll()
                    .requestMatchers(GET, "/topic/**", apiPrefix).permitAll()
                    .requestMatchers(POST, String.format("%s/auction-websocket/**", apiPrefix))
                    .permitAll()
                    .requestMatchers(POST, "/topic/**", apiPrefix).permitAll()

                    .requestMatchers(PUT,
                                     String.format("%s/users**", apiPrefix))
                    .hasAnyRole(Role.MEMBER, Role.STAFF,
                                Role.BREEDER, Role.MANAGER)

                    .requestMatchers(PUT,
                                     String.format("%s/categories/**", apiPrefix))
                    .hasAnyRole(Role.MANAGER)

                    .requestMatchers(DELETE,
                                     String.format("%s/categories/**", apiPrefix))
                    .hasAnyRole(Role.MANAGER)

                    .requestMatchers(GET,
                                     String.format("%s/auction-participant**", apiPrefix))
                    .permitAll()
                    .requestMatchers(POST,
                                     String.format("%s/auction-participant**", apiPrefix))
                    .permitAll()
                    .requestMatchers(PUT,
                                     String.format("%s/auction-participant**", apiPrefix))
                    .permitAll()
                    .requestMatchers(DELETE,
                                     String.format("%s/auction-participant**", apiPrefix))
                    .permitAll()

                    .requestMatchers(GET,
                                     String.format("%s/auction-mail**", apiPrefix)).permitAll()
                    .requestMatchers(POST,
                                     String.format("%s/auction-mail**", apiPrefix)).permitAll()
                    .requestMatchers(PUT,
                                     String.format("%s/auction-mail**", apiPrefix)).permitAll()
                    .requestMatchers(DELETE,
                                     String.format("%s/auction-mail**", apiPrefix)).permitAll()

                    .requestMatchers(GET,
                                     String.format("%s/orders**", apiPrefix)).permitAll()
                    .requestMatchers(POST,
                                     String.format("%s/orders**", apiPrefix)).permitAll()

                    .requestMatchers(GET,
                                     String.format("%s/orders-details**", apiPrefix)).permitAll()
                    .requestMatchers(POST,
                                     String.format("%s/orders-details**", apiPrefix)).permitAll()

                    .anyRequest().authenticated();


            });

        return http.build();
    }
}