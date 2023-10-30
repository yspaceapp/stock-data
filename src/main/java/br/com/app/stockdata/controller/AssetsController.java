package br.com.app.stockdata.controller;

import br.com.app.stockdata.model.Assets;
import br.com.app.stockdata.model.AuthRequest;
import br.com.app.stockdata.model.AuthResponse;
import br.com.app.stockdata.service.AssetsService;
import br.com.app.stockdata.service.AuthService;
import br.com.app.stockdata.service.ExternalApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assets")
@RequiredArgsConstructor
public class AssetsController {

    private final AuthService authService;
    private final ExternalApiService externalApiService;
    private final AssetsService assetsService;


    @GetMapping("/all")
    public List<Assets> allAssets(){
        return assetsService.findAll();
    }

    @GetMapping("/stock")
    public void assets(){
        assetsService.updateListAssets();
    }

}
