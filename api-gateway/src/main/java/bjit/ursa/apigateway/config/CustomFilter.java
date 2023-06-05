package bjit.ursa.apigateway.config;

import bjit.ursa.apigateway.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    private final JwtService jwtService;
    public CustomFilter(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }



    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            System.out.println("Pre filter: " + exchange.getRequest());

            // Extract token from Authorization header
            HttpHeaders headers = exchange.getRequest().getHeaders();
            String authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
            String token = null;
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                //write no token exist
                return exchange.getResponse().setComplete();
            }

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7); // Remove "Bearer " prefix
            }
            System.out.printf(token);
            if (!jwtService.isTokenValid(token)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                //write token invalid to response
                return exchange.getResponse().setComplete();
            }
            List<String> roles = jwtService.extractUserRoles(token);
            System.out.printf(roles.toString());
            exchange.getAttributes().put("roles", roles);



            // Continue the filter chain
            return chain.filter(exchange);
        };
    }
//    private List<String> extractRolesFromToken(ServerHttpRequest request) {
//        // Extract the token from the request headers and decode it
//        // Use JwtDecoder to validate the token and obtain claims
//        // Extract the roles from the claims and return them as a list
//    }


    public static class Config {
    }
}
