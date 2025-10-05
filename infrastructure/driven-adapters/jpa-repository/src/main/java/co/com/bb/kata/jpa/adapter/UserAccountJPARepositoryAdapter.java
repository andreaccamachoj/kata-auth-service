package co.com.bb.kata.jpa.adapter;

import co.com.bb.kata.jpa.UserAccountJPARepository;
import co.com.bb.kata.jpa.entity.RoleEntity;
import co.com.bb.kata.jpa.entity.UserAccountEntity;
import co.com.bb.kata.jpa.helper.AdapterOperations;
import co.com.bb.kata.model.exception.BusinessException;
import co.com.bb.kata.model.exception.TechnicalException;
import co.com.bb.kata.model.exception.message.BusinessExceptionMessage;
import co.com.bb.kata.model.exception.message.TechnicalExceptionMessage;
import co.com.bb.kata.model.useraccount.UserAccount;
import co.com.bb.kata.model.useraccount.gateways.UserAccountRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserAccountJPARepositoryAdapter extends AdapterOperations<
        UserAccount,
        UserAccountEntity,
        Long,
        UserAccountJPARepository>
 implements UserAccountRepository
{

    public UserAccountJPARepositoryAdapter(UserAccountJPARepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, UserAccount.class));
    }

    private static final Logger log = LoggerFactory.getLogger(UserAccountJPARepositoryAdapter.class);

    @Override
    public UserAccount saveUser(UserAccount userAccount) {
        try {
            log.info("[USER-REPO] Saving new user with email: {}", userAccount.getEmail());
            UserAccountEntity entity = mapper.map(userAccount, UserAccountEntity.class);
            RoleEntity roleRef = new RoleEntity();
            roleRef.setIdRole(userAccount.getRoleId());
            entity.setRole(roleRef);
            UserAccountEntity saved = repository.save(entity);
            return mapper.map(saved, UserAccount.class);
        } catch (Exception e) {
            log.error("[USER-REPO] Error saving user: {}", e.getMessage(), e);
            throw new TechnicalException(TechnicalExceptionMessage.SAVING_USER_ERROR);
        }
    }

    @Override
    public boolean existsByEmailOrIdentityDocument(String email, String identityDocument) {
        try {
            boolean exists = repository.existsByEmailIgnoreCase(email)
                    || repository.existsByIdentityDocument(identityDocument);
            log.info("[USER-REPO] Checking if user exists (email={} | doc={}): {}", email, identityDocument, exists);
            return exists;
        } catch (Exception e) {
            log.error("[USER-REPO] Error checking user existence: {}", e.getMessage(), e);
            throw new BusinessException(BusinessExceptionMessage.USER_ALREADY_EXISTS);
        }
    }

    @Override
    public UserAccount findByEmail(String email) {
        try {
            return repository.findByEmail(email)
                    .map(entity -> {
                        UserAccount model = mapper.map(entity, UserAccount.class);
                        if (entity.getRole() != null) {
                            model.setRoleId(entity.getRole().getIdRole());
                        }
                        return model;
                    })
                    .orElseThrow(() -> new TechnicalException(TechnicalExceptionMessage.USER_NOT_FOUND));

        } catch (TechnicalException e) {
            throw e;
        } catch (Exception e) {
            log.error("[USER-REPO] Error finding user by email '{}': {}", email, e.getMessage(), e);
            throw new TechnicalException(TechnicalExceptionMessage.FIND_USER_ERROR);
        }
    }
}
