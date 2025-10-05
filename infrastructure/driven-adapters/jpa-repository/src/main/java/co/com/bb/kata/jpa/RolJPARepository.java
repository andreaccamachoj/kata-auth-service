package co.com.bb.kata.jpa;

import co.com.bb.kata.jpa.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface RolJPARepository extends CrudRepository<RoleEntity, Long>, QueryByExampleExecutor<RoleEntity> {
}
