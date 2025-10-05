package co.com.bb.kata.api;
import co.com.bb.kata.api.dto.request.LoginRequest;
import co.com.bb.kata.api.dto.request.RegisterUserRequest;
import co.com.bb.kata.api.mapper.LoginMapper;
import co.com.bb.kata.api.mapper.RegisterUserMapper;
import co.com.bb.kata.model.login.UserLogged;
import co.com.bb.kata.model.validatetoken.ValidateToken;
import co.com.bb.kata.usecase.auth.AuthUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ApiRest {

    private final AuthUseCase authUseCase;
    private final RegisterUserMapper registerUserMapper;
    private final LoginMapper loginMapper;

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterUserRequest request) {
        authUseCase.registerUser(registerUserMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserLogged> login(@RequestBody LoginRequest request) {
        UserLogged response = authUseCase.login(loginMapper.toDomain(request));
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/validate")
    public ResponseEntity<ValidateToken> validateToken(@RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.replace("Bearer ", "");
        ValidateToken response = authUseCase.validateToken(token);
        return ResponseEntity.ok(response);
    }
}