package co.com.bb.kata.model.useraccount.gateways;

import co.com.bb.kata.model.useraccount.UserAccount;

public interface UserAccountRepository {
    public UserAccount saveUser(UserAccount userAccount);
    public boolean existsByEmailOrIdentityDocument(String email, String identityDocument);
}
