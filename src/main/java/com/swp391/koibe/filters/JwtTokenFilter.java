package com.swp391.koibe.filters;

import com.swp391.koibe.components.JwtTokenUtils;
import com.swp391.koibe.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    @Value("${api.prefix}")
    private String apiPrefix;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            if (isBypassToken(request)) {
                filterChain.doFilter(request, response); // enable bypass
                return;
            }
            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }
            final String token = authHeader.substring(7);
            final String email = jwtTokenUtil.extractEmail(token);
            if (email != null
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                User userDetails = (User) userDetailsService.loadUserByUsername(email);
                if (jwtTokenUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response); // enable bypass
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }

    }

    private boolean isBypassToken(@NonNull HttpServletRequest request) {
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                //RoleController
                Pair.of(String.format("%s/roles", apiPrefix), "GET"),
                Pair.of(String.format("%s/roles", apiPrefix), "POST"),
                Pair.of(String.format("%s/roles", apiPrefix), "PUT"),
                Pair.of(String.format("%s/roles", apiPrefix), "DELETE"),

                //KoiController
                Pair.of(String.format("%s/kois", apiPrefix), "GET"),
                Pair.of(String.format("%s/kois", apiPrefix), "POST"),
                Pair.of(String.format("%s/kois", apiPrefix), "PUT"),
                Pair.of(String.format("%s/kois", apiPrefix), "DELETE"),

                //KoiImageController
                Pair.of(String.format("%s/koiimage", apiPrefix), "GET"),

                //CategoryController
                Pair.of(String.format("%s/categories", apiPrefix), "GET"),
                Pair.of(String.format("%s/categories", apiPrefix), "POST"), //add security later
                Pair.of(String.format("%s/categories", apiPrefix), "GET"), //add security later
                Pair.of(String.format("%s/categories", apiPrefix), "PUT"), //add security later
                Pair.of(String.format("%s/categories", apiPrefix), "DELETE"), //add security later

                //StaffController
                Pair.of(String.format("%s/staffs", apiPrefix), "GET"),
                Pair.of(String.format("%s/staffs", apiPrefix), "POST"),
                Pair.of(String.format("%s/staffs", apiPrefix), "PUT"),
                Pair.of(String.format("%s/staffs", apiPrefix), "DELETE"),

                //ManagerController
                Pair.of(String.format("%s/managers", apiPrefix), "GET"),
                Pair.of(String.format("%s/managers", apiPrefix), "POST"),
                Pair.of(String.format("%s/managers", apiPrefix), "PUT"),
                Pair.of(String.format("%s/managers", apiPrefix), "DELETE"),

                //UserController
                Pair.of(String.format("%s/users/register", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/login", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/details", apiPrefix), "PUT"),
                Pair.of(String.format("%s/users/block", apiPrefix), "PUT"),

                //Verify OTP
                Pair.of(String.format("%s/users/verify", apiPrefix), "PUT"),
                Pair.of(String.format("%s/users/verify", apiPrefix), "POST"),

                Pair.of(String.format("%s/oauth2", apiPrefix), "POST"),
                Pair.of(String.format("%s/oauth2/google-client-id", apiPrefix), "GET"),
                Pair.of(String.format("%s/members", apiPrefix), "GET"),
                Pair.of(String.format("%s/breeders", apiPrefix), "GET"),

                //Otp
                Pair.of(String.format("%s/otp/send", apiPrefix), "GET"),

                //Swagger
                Pair.of("/api-docs","GET"),
                Pair.of("/swagger-resources","GET"),
                Pair.of("/configuration/ui","GET"),
                Pair.of("/configuration/security","GET"),
                Pair.of("/swagger-ui","GET"),
                Pair.of("/swagger-ui.html", "GET"),
                Pair.of("/swagger-ui/index.html", "GET"),

                //Actuator
                Pair.of(String.format("%s/healthcheck/health", apiPrefix), "GET"),
                Pair.of(String.format("%s/actuator", apiPrefix), "GET"),

                //Prometheus
                Pair.of("/actuator/prometheus", "GET"),

                //Auction
                 Pair.of(String.format("%s/auctions", apiPrefix), "GET"),
                 Pair.of(String.format("%s/auctions", apiPrefix), "POST"),
                 Pair.of(String.format("%s/auctions", apiPrefix), "PUT"),
                 Pair.of(String.format("%s/auctions", apiPrefix), "DELETE"),

                //AuctionKoi
                 Pair.of(String.format("%s/auctionkois", apiPrefix), "GET"),
                 Pair.of(String.format("%s/auctionkois", apiPrefix), "POST"),
                 Pair.of(String.format("%s/auctionkois", apiPrefix), "PUT"),
                 Pair.of(String.format("%s/auctionkois", apiPrefix), "DELETE"),

                //AuctionKoiDetail
                Pair.of(String.format("%s/auctionkoidetails", apiPrefix), "GET"),
                Pair.of(String.format("%s/auctionkoidetails", apiPrefix), "POST"),
                Pair.of(String.format("%s/auctionkoidetails", apiPrefix), "PUT"),
                Pair.of(String.format("%s/auctionkoidetails", apiPrefix), "DELETE"),

                //AuctionKoiWebsocket
                Pair.of("/auction-websocket", "GET"),
                Pair.of("/topic", "GET"),
                Pair.of("/auction-websocket", "POST"),
                Pair.of("/topic", "POST")

        // Pair.of(String.format("%s/products/test/view", apiPrefix), "GET")
        );
//        for (Pair<String, String> bypassToken : bypassTokens) {
//            if (request.getServletPath().contains(bypassToken.getFirst()) &&
//                    request.getMethod().equals(bypassToken.getSecond())) {
//                return true;
//            }
//        }
//        return false;

        String servletPath = request.getServletPath();
        String method = request.getMethod();

        for (Pair<String, String> bypassToken : bypassTokens) {
            String bypassPath = bypassToken.getFirst();
            if (servletPath.startsWith(bypassPath) && method.equals(bypassToken.getSecond())) {
                return true;
            }
        }
        return false;

    }
}
