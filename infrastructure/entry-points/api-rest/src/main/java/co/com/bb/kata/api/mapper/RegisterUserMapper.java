package co.com.bb.kata.api.mapper;

import co.com.bb.kata.api.dto.request.RegisterUserRequest;
import co.com.bb.kata.model.registeruser.RegisterUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterUserMapper {

    RegisterUser toDomain(RegisterUserRequest req);
}
