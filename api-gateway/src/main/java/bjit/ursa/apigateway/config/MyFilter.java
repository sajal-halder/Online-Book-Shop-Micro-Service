package bjit.ursa.apigateway.config;

import bjit.ursa.apigateway.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MyFilter implements WebFilter {
    private final JwtService jwtService;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // Extract token from Authorization header
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
        String token = null;
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        token = authorizationHeader.substring(7);

        if (!jwtService.isTokenValid(token)) {
            return  chain.filter(exchange);
        }
        List<String> roles = jwtService.extractUserRoles(token);

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role->authorities.add(new SimpleGrantedAuthority(role)));
        String userEmail = jwtService.extractUserEmail(token);
        User user = new User(userEmail,"",authorities);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userEmail,"",authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));

    }
}
