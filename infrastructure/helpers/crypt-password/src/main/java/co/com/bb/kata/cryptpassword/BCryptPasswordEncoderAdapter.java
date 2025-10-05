package co.com.bb.kata.cryptpassword;

import co.com.bb.kata.model.credential.gateways.PasswordEncoderGateway;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BCryptPasswordEncoderAdapter implements PasswordEncoderGateway {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public Boolean matches(String raw, String hash) {
        if (raw == null || hash == null) {
            return false;
        }
        return encoder.matches(raw, hash);
    }

    @Override
    public String encode(String raw) {
        if (raw == null) {
            return null;
        }
        return encoder.encode(raw);
    }
}