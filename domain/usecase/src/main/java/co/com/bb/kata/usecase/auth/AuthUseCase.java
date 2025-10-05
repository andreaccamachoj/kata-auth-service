package co.com.bb.kata.usecase.auth;

import co.com.bb.kata.model.credential.Credential;
import co.com.bb.kata.model.credential.gateways.CredentialRepository;
import co.com.bb.kata.model.credential.gateways.PasswordEncoderGateway;
import co.com.bb.kata.model.exception.BusinessException;
import co.com.bb.kata.model.exception.TechnicalException;
import co.com.bb.kata.model.exception.message.BusinessExceptionMessage;
import co.com.bb.kata.model.exception.message.TechnicalExceptionMessage;
import co.com.bb.kata.model.jwt.JwtGateway;
import co.com.bb.kata.model.login.Login;
import co.com.bb.kata.model.login.UserLogged;
import co.com.bb.kata.model.registeruser.RegisterUser;
import co.com.bb.kata.model.rol.gateways.RolRepository;
import co.com.bb.kata.model.tokensession.TokenSession;
import co.com.bb.kata.model.tokensession.gateways.TokenSessionRepository;
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
    private final JwtGateway jwtGateway;
    private final TokenSessionRepository tokenSessionRepository;

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

        UserAccount user = userAccountRepository.findByEmail(request.getEmail());
        String storedHash = credentialRepository.findPasswordHashByUserId(user.getId());

        boolean valid = passwordEncoderGateway.matches(request.getPassword(), storedHash);
        if (!valid) {
            throw new TechnicalException(TechnicalExceptionMessage.INVALID_CREDENTIALS);
        }

        TokenSession token = tokenSessionRepository.saveTokenSession(jwtGateway.generateToken(user));

        return UserLogged.builder()
                .token(token.getToken())
                .userName(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .role(rolRepository.findNameByIdRole(user.getRoleId()))
                .build();
    }

    public ValidateToken validateToken(String token) {
        try {
            UserLogged userLogged = jwtGateway.validateToken(token);

            return ValidateToken.builder()
                    .valid(true)
                    .subject(userLogged.getEmail())
                    .role(userLogged.getRole())
                    .build();

        } catch (TechnicalException e) {
            throw new BusinessException(BusinessExceptionMessage.INVALID_OR_EXPIRED_TOKEN);

        }
    }

}