package co.com.bb.kata.api.mapper;

import co.com.bb.kata.api.dto.request.LoginRequest;
import co.com.bb.kata.model.login.Login;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapper {
    Login toDomain(LoginRequest req);
}