package br.com.app.stockdata.repository;

import br.com.app.stockdata.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {



}
