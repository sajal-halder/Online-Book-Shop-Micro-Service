//package bjit.ursa.apigateway.config;
//
//import bjit.ursa.apigateway.filter.RoleBasedAuthorizationFilter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class GatewayConfig {
//
//    private final RoleBasedAuthorizationFilter roleBasedAuthorizationFilter;
//
//    public GatewayConfig(RoleBasedAuthorizationFilter roleBasedAuthorizationFilter) {
//        this.roleBasedAuthorizationFilter = roleBasedAuthorizationFilter;
//    }
//
//    @Bean
//    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("admin_route", r -> r.path("/admin/**")
//                        .filters(f -> f.filter(roleBasedAuthorizationFilter))
//                        .uri("http://admin-service"))
//                .build();
//    }
//}
//
