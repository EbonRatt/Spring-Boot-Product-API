package com.api.product.model.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Data
public class BaseResponse {

    protected String message;
    private HttpStatus httpStatus;
    private Timestamp timestamp;


}
