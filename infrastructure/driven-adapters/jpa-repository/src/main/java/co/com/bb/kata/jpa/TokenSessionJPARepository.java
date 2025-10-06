package co.com.bb.kata.jpa;

import co.com.bb.kata.jpa.entity.TokenSessionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface TokenSessionJPARepository extends CrudRepository<TokenSessionEntity, Long>, QueryByExampleExecutor<TokenSessionEntity> {


    List<TokenSessionEntity> findByUserAccountEntity_IdAndIsActiveTrue(Long userId);
}