package br.com.app.stockdata.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include. NON_NULL)
public class AuthResponse implements Serializable {
    private String token;
    private String message;
    private HttpStatus httpStatus;
    private Integer code;
}
