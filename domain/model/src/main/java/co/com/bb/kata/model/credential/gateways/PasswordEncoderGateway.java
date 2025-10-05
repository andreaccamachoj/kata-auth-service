package co.com.bb.kata.model.credential.gateways;

public interface PasswordEncoderGateway {
    Boolean matches(String raw, String hash);
    String encode(String raw);
}
