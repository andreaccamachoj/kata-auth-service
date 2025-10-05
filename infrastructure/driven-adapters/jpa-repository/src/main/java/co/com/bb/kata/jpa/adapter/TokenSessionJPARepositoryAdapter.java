package co.com.bb.kata.jpa.adapter;

import co.com.bb.kata.jpa.TokenSessionJPARepository;
import co.com.bb.kata.jpa.entity.TokenSessionEntity;
import co.com.bb.kata.jpa.entity.UserAccountEntity;
import co.com.bb.kata.jpa.helper.AdapterOperations;
import co.com.bb.kata.model.exception.TechnicalException;
import co.com.bb.kata.model.exception.message.TechnicalExceptionMessage;
import co.com.bb.kata.model.tokensession.TokenSession;
import co.com.bb.kata.model.tokensession.gateways.TokenSessionRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TokenSessionJPARepositoryAdapter extends AdapterOperations<
        TokenSession,
        TokenSessionEntity,
        Long,
        TokenSessionJPARepository>
        implements TokenSessionRepository
{

    public TokenSessionJPARepositoryAdapter(TokenSessionJPARepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, TokenSession.class));
    }

    private static final Logger log = LoggerFactory.getLogger(TokenSessionJPARepositoryAdapter.class);


    @Override
    public TokenSession saveTokenSession(TokenSession tokenSession) {
        log.info("[TOKEN-REPO] Starting token session save for userId={}", tokenSession.getUserAccountEntityId());
        try {
            TokenSessionEntity entity = mapper.map(tokenSession, TokenSessionEntity.class);

            UserAccountEntity userEntity = new UserAccountEntity();
            userEntity.setId(tokenSession.getUserAccountEntityId());
            entity.setUserAccountEntity(userEntity);

            TokenSessionEntity saved = repository.save(entity);

            TokenSession result = mapper.map(saved, TokenSession.class);

            log.info("[TOKEN-REPO] Token session saved successfully: idTokenSession={}, userId={}",
                    result.getIdTokenSession(), tokenSession.getUserAccountEntityId());
            return result;

        } catch (Exception e) {
            log.error("[TOKEN-REPO] Error saving token session for userId={}: {}",
                    tokenSession.getUserAccountEntityId(), e.getMessage(), e);
            throw new TechnicalException(TechnicalExceptionMessage.SAVING_TOKEN_SESSION_ERROR);
        }
    }

}