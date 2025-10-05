package co.com.bb.kata.jpa.adapter;

import co.com.bb.kata.jpa.RolJPARepository;
import co.com.bb.kata.jpa.entity.RoleEntity;
import co.com.bb.kata.jpa.helper.AdapterOperations;
import co.com.bb.kata.model.rol.Rol;
import co.com.bb.kata.model.rol.gateways.RolRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class RolJPARepositoryAdapter extends AdapterOperations<
        Rol,
        RoleEntity,
        Long,
        RolJPARepository>
 implements RolRepository
{

    public RolJPARepositoryAdapter(RolJPARepository repository, ObjectMapper mapper) {
    super(repository, mapper, d -> mapper.map(d, Rol.class));
}

    private static final Logger log = LoggerFactory.getLogger(RolJPARepositoryAdapter.class);

    @Override
    public boolean existsById(Long idRol) {
        try {
            boolean exists = repository.existsById(idRol);
            log.info("[ROL-REPO] Checking if role with ID {} exists: {}", idRol, exists);
            return exists;
        } catch (Exception e) {
            log.error("[ROL-REPO] Error checking role existence for ID {}: {}", idRol, e.getMessage(), e);
            return false;
        }
    }
}