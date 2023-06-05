package bjit.ursa.authservertest.controller;

import bjit.ursa.authservertest.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class TestController {

    @Autowired
    JwtService  jwtService;
    @Autowired
    UserDetailsService userDetailsService;

    @GetMapping("/login")
    public String  getToken(){

       return jwtService.generateToken(userDetailsService.loadUserByUsername(""));

    }
}
