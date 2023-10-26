package br.com.app.stockdata.service.impl;

import br.com.app.stockdata.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketServiceImpl implements TicketService {

    @Value("${URL_TICKETS}")
    String urlTickets;


    private final TicketRepository ticketRepository;

    private final RestTemplate restTemplate;

    @Override
    public void fetchDataFromExternalApi() {

        try {
            String apiUrl = urlTickets ;
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                var result = ConvertUtils.convertJsonToObject(responseEntity.getBody(), ResultDTO.class);
                result.getStocks().forEach(stock -> {
                    if(!String.valueOf(stock.charAt(stock.length() - 1)).equals("F")){
                        Ticket ticket = new Ticket();
                        ticket.setSymbol(stock);
                        ticket.setType(Type.STOCK);
                        ticketRepository.saveAndFlush(ticket);
                    }
                });
            } else if (responseEntity.getStatusCode().is4xxClientError()) {
                throw new HttpClientErrorException(responseEntity.getStatusCode(), "Error client ");
            } else if (responseEntity.getStatusCode().is5xxServerError()) {
                throw new HttpClientErrorException(responseEntity.getStatusCode(), "Error Intern ");
            }
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }



}
