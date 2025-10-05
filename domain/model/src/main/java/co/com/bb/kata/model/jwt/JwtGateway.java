package co.com.bb.kata.model.jwt;

import co.com.bb.kata.model.login.UserLogged;
import co.com.bb.kata.model.tokensession.TokenSession;
import co.com.bb.kata.model.useraccount.UserAccount;

public interface JwtGateway {
    public TokenSession generateToken(UserAccount userAccount);
    public UserLogged validateToken(String token);
}