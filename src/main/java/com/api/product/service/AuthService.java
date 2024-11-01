package com.api.product.service;

import com.api.product.model.entities.Gender;
import com.api.product.model.entities.Role;
import com.api.product.model.entities.User;
import com.api.product.model.reponse.AuthResponse;
import com.api.product.model.request.RegisterRequest;
import com.api.product.model.request.UserRequest;
import com.api.product.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String Login(UserRequest userRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.getEmail(),
                        userRequest.getPassword()
                )
        );
        User user = userRepository.findByEmail(userRequest.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(user.getEmail());
        return jwtToken;
    }

    public String Register(RegisterRequest registerRequest) {
        User user = new User();
        if(CheckEmpty(registerRequest.getName()) && CheckEmpty(registerRequest.getEmail()) && CheckEmpty(registerRequest.getPassword())){
            user.setName(registerRequest.getName());
            user.setEmail(registerRequest.getEmail());
            user.setPwd(passwordEncoder.encode(registerRequest.getPassword()));
            user.setGender(Gender.valueOf(registerRequest.getGender()));
            user.setRole(Role.USER);
            userRepository.save(user);

            String jwtToken = jwtService.generateToken(user.getEmail());
            return jwtToken;
        }
        else{
            throw new RuntimeException("Please Fill Product The Name, Email Or Password");
        }


    }

    private boolean CheckEmpty(String productName){
        return productName != null && !productName.isEmpty();
    }
}
