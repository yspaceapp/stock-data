package br.com.app.space.service.impl;

import br.com.app.stockdata.constants.MessagesConstants;
import br.com.app.stockdata.model.AuthRequest;
import br.com.app.stockdata.model.Users;
import br.com.app.stockdata.repository.UserRepository;
import br.com.app.stockdata.security.JwtService;
import br.com.app.stockdata.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl unit;
    @Mock
    private UserRepository userRepository;
    @Mock
    private  PasswordEncoder passwordEncoder;
    @Mock
    private  JwtService jwtService;
    @Mock
    private  AuthenticationManager authenticationManager;

    @Test
    void should_return_entity_enpty_register_request_null(){
       var response = unit.register(null);
        Assertions.assertEquals(response.getMessage(), MessagesConstants.EMPTY_REQUEST);
    }

    @Test
    void should_return_sucessful_register_user_email_valid(){
        var request =  new AuthRequest();
        request.setEmail("Email@gmail.com");
        var response = unit.register(request);
        Assertions.assertEquals(response.getMessage(), MessagesConstants.SUCCESSFUL_TOKEN_GENERATOR);
    }

    @Test
    void should_return_entity_enpty_register_user_email_invalid(){
        var request =  new AuthRequest();
        request.setEmail("..gmail..com");
        var response = unit.register(request);
        Assertions.assertEquals(response.getMessage(), MessagesConstants.INVALID_EMAIL);
    }

    @Test
    void should_return_entity_enpty_register_user_email_valid_and_user_exists(){
        var request =  new AuthRequest();
        request.setEmail("email@gmail.com");
        Mockito.when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.ofNullable(Users.builder().email("email@gmail.com").build()));
        var response = unit.register(request);
        Assertions.assertEquals(response.getMessage(), MessagesConstants.USER_ALREADY_EXISTS);
    }
}