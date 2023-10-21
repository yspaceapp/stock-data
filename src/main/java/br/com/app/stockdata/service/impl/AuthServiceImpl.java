package br.com.app.stockdata.service.impl;

import br.com.app.stockdata.Utils.ValidatorUtils;
import br.com.app.stockdata.constants.MessagesConstants;
import br.com.app.stockdata.repository.UserRepository;
import br.com.app.stockdata.security.JwtService;
import br.com.app.stockdata.service.AuthService;
import br.com.app.stockdata.model.AuthRequest;
import br.com.app.stockdata.model.AuthResponse;
import br.com.app.stockdata.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(AuthRequest request) {
        if (request == null) {
            return AuthResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(MessagesConstants.EMPTY_REQUEST)
                    .build();
        }
        if (!ValidatorUtils.isEmailValido(request.getEmail())) {
            return AuthResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(MessagesConstants.INVALID_EMAIL)
                    .build();
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return AuthResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(MessagesConstants.USER_ALREADY_EXISTS)
                    .build();
        }

        var user = Users.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .activated(true)
                .createAt(new Date())
                .build();
        var userSaved = userRepository.save(user);

        return AuthResponse.builder()
                .message(MessagesConstants.SUCCESSFUL_TOKEN_GENERATOR)
                .code(HttpStatus.CREATED.value()).build();
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            user.setLastAccess(new Date());
            userRepository.saveAndFlush(user);
            return AuthResponse.builder()
                    .token(generateToken(user)).build();
        } catch (Exception e) {
            return AuthResponse.builder()
                    .message(MessagesConstants.AUTHENTICATE_ERROR).build();
        }
    }

    private String generateToken(Users user) {
        return jwtService.generateToken(user);
    }

}
