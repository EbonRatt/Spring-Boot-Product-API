package com.api.product.model.request;

import com.api.product.model.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String name;
    private String gender;
    private String email;
    private String password;
    private Role role;
}
