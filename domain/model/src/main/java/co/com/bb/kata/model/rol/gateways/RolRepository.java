package co.com.bb.kata.model.rol.gateways;

public interface RolRepository {
    public boolean existsById(Long idRol);
    public String findNameByIdRole(Long idRole);
}
