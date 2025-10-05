package co.com.bb.kata.jpa;

import co.com.bb.kata.jpa.entity.UserAccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface UserAccountJPARepository extends CrudRepository<UserAccountEntity, Long>, QueryByExampleExecutor<UserAccountEntity> {
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByIdentityDocument(String identityDocument);
}
