package co.com.bb.kata.model.tokensession.gateways;

import co.com.bb.kata.model.tokensession.TokenSession;

public interface TokenSessionRepository {
    public TokenSession saveTokenSession(TokenSession tokenSession);
}
