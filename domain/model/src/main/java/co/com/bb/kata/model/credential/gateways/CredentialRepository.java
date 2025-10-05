package co.com.bb.kata.model.credential.gateways;

import co.com.bb.kata.model.credential.Credential;

public interface CredentialRepository {
    public Credential saveCredential(Credential credential);
}
