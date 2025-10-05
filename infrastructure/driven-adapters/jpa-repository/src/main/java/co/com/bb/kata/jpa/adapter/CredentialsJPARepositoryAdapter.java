package co.com.bb.kata.jpa.adapter;

import co.com.bb.kata.jpa.CredentialsJPARepository;
import co.com.bb.kata.jpa.entity.CredentialsEntity;
import co.com.bb.kata.jpa.entity.UserAccountEntity;
import co.com.bb.kata.jpa.helper.AdapterOperations;
import co.com.bb.kata.model.credential.Credential;
import co.com.bb.kata.model.credential.gateways.CredentialRepository;
import co.com.bb.kata.model.exception.TechnicalException;
import co.com.bb.kata.model.exception.message.TechnicalExceptionMessage;
import org.reactivecommons.utils.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CredentialsJPARepositoryAdapter extends AdapterOperations<
        Credential,
        CredentialsEntity,
        Long,
        CredentialsJPARepository>
        implements CredentialRepository
{

    public CredentialsJPARepositoryAdapter(CredentialsJPARepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Credential.class));
    }

    private static final Logger log = LoggerFactory.getLogger(CredentialsJPARepositoryAdapter.class);

    @Override
    @Transactional
    public Credential saveCredential(Credential credential) {
        try {
            log.info("[CREDENTIALS-REPO] Guardando credenciales para usuario ID {}", credential.getUserAccountId());

            CredentialsEntity entity = mapper.map(credential, CredentialsEntity.class);

            UserAccountEntity userRef = new UserAccountEntity();
            userRef.setId(credential.getUserAccountId());
            entity.setUserAccountEntity(userRef);

            CredentialsEntity saved = repository.save(entity);

            return mapper.map(saved, Credential.class);

        } catch (Exception e) {
            log.error("[CREDENTIALS-REPO] Error al guardar credenciales: {}", e.getMessage(), e);
            throw new TechnicalException(TechnicalExceptionMessage.SAVING_CREDENTIALS_ERROR);
        }
    }
}
