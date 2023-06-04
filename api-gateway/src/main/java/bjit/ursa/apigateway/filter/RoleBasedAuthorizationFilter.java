package bjit.ursa.apigateway.filter;

import bjit.ursa.apigateway.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component

public class RoleBasedAuthorizationFilter implements GatewayFilter {

    private static final String ROLE_CLAIM = "roles";
    @Autowired
    JwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        List<String> roles = extractUserRolesFromRequest(request);

        if (roles.contains("ADMIN")) {
            // User has ADMIN role, allow access
            return chain.filter(exchange);
        } else {
            // User doesn't have ADMIN role, deny access
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }
    }

    private List<String> extractUserRolesFromRequest(ServerHttpRequest request) {

        HttpHeaders headers = request.getHeaders();
        String authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
            jwtService.isTokenValid(token);

        }

        return null;
    }
}

