//package bjit.ursa.apigateway.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//
//@Configuration
//@RequiredArgsConstructor
//public class SecurityConfig {
//    private final MyFilter myFilter;
//    @Bean
//    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
//        return http
//                .csrf()
//                .disable()
//                .authorizeExchange()
//                .pathMatchers("/auth-server/**")
//                .permitAll()
//                .pathMatchers("/book-service/**")
//                .hasAuthority("USER")
//                .anyExchange().authenticated().and()
//                .httpBasic().and()
//                .addFilterBefore(myFilter, SecurityWebFiltersOrder.REACTOR_CONTEXT)
//                .build();
//    }
//}
