package br.com.app.stockdata.controller;

import br.com.app.stockdata.model.Assets;
import br.com.app.stockdata.service.AssetsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assets")
@RequiredArgsConstructor
public class AssetsController {

    private final AssetsService assetsService;


    @GetMapping("/all")
    public List<Assets> allAssets(){
        return assetsService.findAll();
    }

    @GetMapping("/{type}")
    public void updateListAssets(@PathVariable("type") String type){
        assetsService.updateListAssets(type);
    }

}
