package co.com.bb.kata.api;
import co.com.bb.kata.api.dto.request.LoginRequest;
import co.com.bb.kata.api.dto.request.RegisterUserRequest;
import co.com.bb.kata.api.dto.request.UserRequest;
import co.com.bb.kata.api.handler.AuthHandler;
import co.com.bb.kata.model.login.UserLogged;
import co.com.bb.kata.model.useraccount.UserAccount;
import co.com.bb.kata.model.validatetoken.ValidateToken;
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

    private final AuthHandler handler;

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterUserRequest request) {
        String response = handler.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserLogged> login(@RequestBody LoginRequest request) {
        UserLogged response = handler.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/validate")
    public ResponseEntity<ValidateToken> validateToken(@RequestHeader("Authorization") String tokenHeader) {
        ValidateToken response = handler.validateToken(tokenHeader);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/logout/{userId}")
    public ResponseEntity<Void> logout(@PathVariable("userId") Long userId) {
        handler.logout(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/user")
    public ResponseEntity<UserAccount> getUserByEmail(@RequestBody UserRequest userRequest) {
        UserAccount user = handler.getUserByUserId(userRequest.getUserId());
        return ResponseEntity.ok(user);
    }
}