package br.com.app.stockdata.controller;

import br.com.app.stockdata.model.AuthRequest;
import br.com.app.stockdata.model.AuthResponse;
import br.com.app.stockdata.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private  final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody AuthRequest request){
        return authService.register(request);
    }

    @PostMapping("/authenticate")
    public AuthResponse authenticate(@RequestBody AuthRequest request){
        return authService.authenticate(request);
    }

}
