package co.com.bb.kata.jpa;

import co.com.bb.kata.jpa.entity.CredentialsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface CredentialsJPARepository extends CrudRepository<CredentialsEntity, Long>, QueryByExampleExecutor<CredentialsEntity> {
}