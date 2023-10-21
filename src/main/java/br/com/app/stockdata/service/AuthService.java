package br.com.app.stockdata.service;

import br.com.app.stockdata.model.AuthRequest;
import br.com.app.stockdata.model.AuthResponse;

public interface AuthService {

    AuthResponse register(AuthRequest request);

    AuthResponse authenticate(AuthRequest request);
}
