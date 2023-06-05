//package bjit.ursa.apigateway.config;
//
//import bjit.ursa.apigateway.JwtService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.http.HttpHeaders;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class GateWayFilter  implements GatewayFilter {
//    private final JwtService jwtService;
//    private final AuthenticationManager authenticationManager;
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        HttpHeaders headers = exchange.getRequest().getHeaders();
//        String authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
//        String token = null;
//        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//            return chain.filter(exchange);
//        }
//
//        token = authorizationHeader.substring(7);
//
//        if (!jwtService.isTokenValid(token)) {
//            return  chain.filter(exchange);
//        }
//        List<String> roles = jwtService.extractUserRoles(token);
//
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        roles.forEach(role->authorities.add(new SimpleGrantedAuthority(role)));
//        String userEmail = jwtService.extractUserEmail(token);
//        User user = new User(userEmail,"",authorities);
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userEmail,"",authorities);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        return  chain.filter(exchange);
//    }
//}
