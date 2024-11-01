package com.api.product.model.reponse;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class AuthResponse extends BaseResponse{

    private String token;
}
