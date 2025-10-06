package co.com.bb.kata.api.handler;

import co.com.bb.kata.api.dto.request.LoginRequest;
import co.com.bb.kata.api.dto.request.RegisterUserRequest;
import co.com.bb.kata.api.mapper.LoginMapper;
import co.com.bb.kata.api.mapper.RegisterUserMapper;
import co.com.bb.kata.model.login.Login;
import co.com.bb.kata.model.login.UserLogged;
import co.com.bb.kata.model.registeruser.RegisterUser;
import co.com.bb.kata.model.useraccount.UserAccount;
import co.com.bb.kata.model.validatetoken.ValidateToken;
import co.com.bb.kata.usecase.auth.AuthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthHandler {

    private final AuthUseCase authUseCase;
    private final RegisterUserMapper registerUserMapper;
    private final LoginMapper loginMapper;

    public String registerUser(RegisterUserRequest request) {
        RegisterUser user = registerUserMapper.toDomain(request);
        authUseCase.registerUser(user);
        return "User registered successfully";
    }

    public UserLogged login(LoginRequest request) {
        Login login = loginMapper.toDomain(request);
        return authUseCase.login(login);
    }

    public ValidateToken validateToken(String tokenHeader) {
        String token = tokenHeader.replace("Bearer ", "");
        return authUseCase.validateToken(token);
    }

    public void logout(Long userId) {
        authUseCase.deactivateTokensByUserId(userId);
    }

    public UserAccount getUserByUserId(Long userId) {
        return authUseCase.getUserByIdUsuario(userId);
    }


}
