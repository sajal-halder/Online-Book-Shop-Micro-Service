package bjit.ursa.apigateway.config;

import bjit.ursa.apigateway.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

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
                return exchange.getResponse().setComplete();
            }

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7); // Remove "Bearer " prefix
            }
            System.out.printf(token);

            // Perform token validation and authentication

            // Continue the filter chain
            return chain.filter(exchange);
        };
    }


    public static class Config {
    }
}
