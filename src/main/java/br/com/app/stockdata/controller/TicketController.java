package br.com.app.stockdata.controller;

import br.com.app.stockdata.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("/stocks")
    public void fetchListStocks(){
        ticketService.fetchDataFromExternalApi();
    }


}
