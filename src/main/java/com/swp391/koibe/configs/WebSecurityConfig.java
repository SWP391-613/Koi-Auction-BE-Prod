package com.swp391.koibe.configs;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import com.swp391.koibe.filters.JwtTokenFilter;
import com.swp391.koibe.models.Role;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.util.Pair;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
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
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests
                            .requestMatchers(
                                //actuator
                                String.format("%s/actuator/**", apiPrefix),
                                String.format("%s/actuator/prometheus", apiPrefix),
                                String.format("%s/actuator/info", apiPrefix),
                                String.format("%s/actuator/health", apiPrefix),

                                //prometheus
                                "/actuator/prometheus",

                                String.format("%s/healthcheck/**", apiPrefix),

                                // swagger
                                // "/v3/api-docs",
                                // "/v3/api-docs/**",
                                "/api-docs",
                                "/api-docs/**",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/configuration/ui",
                                "/configuration/security",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/webjars/swagger-ui/**",
                                "/swagger-ui/index.html",
                                "/topic/**"

                            )
                            .permitAll()

                            //UserController
                            .requestMatchers(GET, String.format("%s/users/{id:\\d+}", apiPrefix)).permitAll()
                            .requestMatchers(POST, String.format("%s/users/details", apiPrefix)).hasAnyRole(Role.MANAGER, Role.MEMBER, Role.BREEDER, Role.STAFF)
                            .requestMatchers(POST, String.format("%s/users/login", apiPrefix)).permitAll()
                            .requestMatchers(POST, String.format("%s/users/register", apiPrefix)).permitAll()
                            .requestMatchers(PUT,String.format(("%s/users/verify/{otp:\\d+}"),apiPrefix)).permitAll()
                            .requestMatchers(POST,String.format(("%s/users/verify"),apiPrefix)).permitAll()
                            .requestMatchers(POST, String.format("%s/users/logout", apiPrefix)).
                            hasAnyRole(Role.MANAGER, Role.MEMBER, Role.BREEDER, Role.STAFF)
                            .requestMatchers(PUT, String.format("%s/users/{userId:\\d+}/deposit/{payment:\\d+}", apiPrefix))
                            .hasAnyRole(Role.MANAGER, Role.MEMBER, Role.BREEDER, Role.STAFF)

                            //GET: breeders .permitAll()
                            .requestMatchers(GET, String.format("%s/breeders", apiPrefix)).permitAll()

                            .requestMatchers(GET,
                                    String.format("%s/roles", apiPrefix))
                            .permitAll()
                            .requestMatchers(GET,
                                         String.format("%s/roles/{id:\\d+}", apiPrefix))
                            .permitAll()

                            .requestMatchers(POST,
                                    String.format("%s/autho2/**", apiPrefix))
                            .permitAll()

                            .requestMatchers(GET,
                                    String.format("%s/categories**", apiPrefix))
                            .permitAll()

                            .requestMatchers(GET,
                                    String.format("%s/auction-websocket/**", apiPrefix))
                            .permitAll()

                            .requestMatchers(POST,
                                    String.format("%s/auction-websocket/**",
                                            apiPrefix))
                            .permitAll()

                            .requestMatchers(GET,
                                    String.format("%s/auction-participant**",
                                            apiPrefix))
                            .permitAll()

                            .requestMatchers(GET,
                                    String.format("%s/auction-mail**", apiPrefix))
                            .permitAll()

                            // AuctionController******************
                            .requestMatchers(GET, String.format("%s/auctions**", apiPrefix)).permitAll()
                            .requestMatchers(PUT, String.format("%s/auctions/{id:\\d+}", apiPrefix)).hasAnyRole(Role.MANAGER, Role.STAFF)
                            .requestMatchers(POST, String.format("%s/auctions", apiPrefix)).hasAnyRole(Role.MANAGER)
                            .requestMatchers(DELETE, String.format("%s/auctions/{id:\\d+}", apiPrefix)).hasAnyRole(Role.MANAGER)
                            .requestMatchers(GET, String.format("%s/auctions/{id:\\d+}", apiPrefix)).permitAll()
                            .requestMatchers(GET, String.format("%s/auctions/koi_register", apiPrefix)).hasAnyRole(Role.BREEDER)
                            // ******************************8

                            // AuctionKoiController******************
                            .requestMatchers(GET,
                                    String.format("%s/auctionkois**", apiPrefix))
                            .permitAll()

                            .requestMatchers(GET,
                                    String.format("%s/auctionkois/auction/{id:\\d+}", apiPrefix))
                            .permitAll()
                            .requestMatchers(GET,
                                    String.format("%s/auctionkois/{aid:\\d+}/{id:\\d+}", apiPrefix))
                            .permitAll()

                            .requestMatchers(GET,
                                    String.format("%s/bidding/{id:\\d+}", apiPrefix))
                            .permitAll()
                            .requestMatchers(GET,
                                    String.format("%s/bidding/{auctionKoiId:\\d+}/{userId:\\d+}", apiPrefix))
                            .permitAll()

                            .requestMatchers(GET,
                                    String.format("%s/auctionkois/get-kois-by-keyword", apiPrefix))
                            .permitAll()

                            .requestMatchers(GET,
                                    String.format("%s/orders**", apiPrefix))
                            .permitAll()

                            .requestMatchers(GET,
                                    String.format("%s/orders/get-orders-by-keyword", apiPrefix))
                            .permitAll()

                            .requestMatchers(GET, String.format("%s/kois/{id:\\d+}", apiPrefix))
                            .permitAll()

                            .requestMatchers(PUT, String.format("%s/kois/{id:\\d+}", apiPrefix))
                            .hasAnyRole(Role.MANAGER, Role.STAFF, Role.BREEDER)

                            .requestMatchers(GET,
                                    String.format("%s/kois/get-kois-by-keyword", apiPrefix))
                            .hasAnyRole(Role.BREEDER)
                            //get-kois-owner-by-keyword-not-auth
                            .requestMatchers(GET,
                                    String.format("%s/kois/get-kois-owner-by-keyword-not-auth", apiPrefix))
                            .permitAll()
                            .requestMatchers(GET,
                                    String.format("%s/kois/get-unverified-kois-by-keyword", apiPrefix))
                            .hasAnyRole(Role.MANAGER, Role.STAFF, Role.BREEDER)

                            // Already put this in PreAuthorize
                            .requestMatchers(GET,
                                    String.format("%s/auctions/get-auctions-by-keyword", apiPrefix))
                            .permitAll()

                            .requestMatchers(GET,
                                    String.format("%s/orders_details**", apiPrefix))
                            .permitAll()

                            .requestMatchers(GET,
                                    String.format("%s/kois**", apiPrefix))
                            .permitAll()

                            // StaffController: all route need to verify JWT Token
                            .requestMatchers(GET,
                                    String.format("%s/staffs**", apiPrefix))
                            .hasAnyRole(Role.MANAGER)

                            .requestMatchers(GET,
                                    String.format("%s/payment**", apiPrefix))
                            .hasAnyRole(Role.MEMBER, Role.BREEDER)

                            // forgot-password
                            .requestMatchers(GET, String.format("%s/forgot-password**", apiPrefix))
                            .permitAll()
                            .requestMatchers(PUT, String.format("%s/forgot-password**", apiPrefix))
                            .permitAll()
                            // .requestMatchers(GET, "/api/v1/payments/vnpay/payment_return")
                            // .permitAll()

                            // OtpController
                            .requestMatchers(GET, String.format("%s/otp/send", apiPrefix)).permitAll()
                            .requestMatchers(POST, String.format("%s/otp/verify", apiPrefix)).permitAll()

                            // feedbackcontroller
                            .requestMatchers(GET, String.format("%s/feedbacks**", apiPrefix)).permitAll()
                            .requestMatchers(POST, String.format("%s/feedbacks**", apiPrefix)).permitAll()
                            .requestMatchers(PUT, String.format("%s/feedbacks**", apiPrefix)).permitAll()
                            .requestMatchers(DELETE, String.format("%s/feedbacks**", apiPrefix)).permitAll()

                            // MailController
                            .requestMatchers(GET, String.format("%s/mail/update-role", apiPrefix)).hasAnyRole(Role.MEMBER)

                            // Add this new rule to allow access to the VNPay payment return endpoint
                            .requestMatchers(GET,
                                    String.format("%s/payments/vnpay/payment_return", apiPrefix))
                            .permitAll()

                            .anyRequest().authenticated();
                })
                .csrf(AbstractHttpConfigurer::disable);
        ;

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000", // Your frontend local
                "http://10.0.2.2:4000", // Android emulator
                "https://*.lt", // Localtunnel domains
                "https://www.koiauction88.me", // Your domain
                "https://koiauction88.me", // Non-www version
                "https://*.ngrok-free.app" // ngrok domains
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "Accept",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers"));
        configuration.setExposedHeaders(Arrays.asList(
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Credentials"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
