package co.com.bb.kata.usecase.auth;

import co.com.bb.kata.model.credential.Credential;
import co.com.bb.kata.model.credential.gateways.CredentialRepository;
import co.com.bb.kata.model.credential.gateways.PasswordEncoderGateway;
import co.com.bb.kata.model.login.Login;
import co.com.bb.kata.model.login.UserLogged;
import co.com.bb.kata.model.registeruser.RegisterUser;
import co.com.bb.kata.model.rol.gateways.RolRepository;
import co.com.bb.kata.model.useraccount.UserAccount;
import co.com.bb.kata.model.useraccount.gateways.UserAccountRepository;
import co.com.bb.kata.model.validatetoken.ValidateToken;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthUseCase {

    private final RolRepository rolRepository;
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoderGateway passwordEncoderGateway;
    private final CredentialRepository credentialRepository;

    public void registerUser(RegisterUser request) {

        rolRepository.existsById(request.getRoleId());
        userAccountRepository.existsByEmailOrIdentityDocument(
                request.getEmail(), request.getIdentityDocument()
        );

        UserAccount user = UserAccount.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .identityDocument(request.getIdentityDocument())
                .birthDate(request.getBirthDate())
                .phone(request.getPhone())
                .roleId(request.getRoleId())
                .build();

        UserAccount savedUser = userAccountRepository.saveUser(user);
        String hashedPassword = passwordEncoderGateway.encode(request.getPassword());

        Credential credential = Credential.builder()
                .userAccountId(savedUser.getId())
                .passwordHash(hashedPassword)
                .build();

        credentialRepository.saveCredential(credential);
    }

    public UserLogged login(Login request) {
        // Lógica de login (validar credenciales, generar JWT)
        return UserLogged.builder()
                .token("fake-jwt-token")
                .userName("Andrea C.")
                .email(request.getEmail())
                .role("USER")
                .build();
    }

    public ValidateToken validateToken(String token) {
        // Lógica de validación de token (decodificar JWT, validar expiración)
        return ValidateToken.builder()
                .valid(true)
                .subject("user@example.com")
                .role("USER")
                .build();
    }

}