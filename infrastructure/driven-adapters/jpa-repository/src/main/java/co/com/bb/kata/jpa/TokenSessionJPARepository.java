package co.com.bb.kata.jpa;

import co.com.bb.kata.jpa.entity.TokenSessionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface TokenSessionJPARepository extends CrudRepository<TokenSessionEntity, Long>, QueryByExampleExecutor<TokenSessionEntity> {

}