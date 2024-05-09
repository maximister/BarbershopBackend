package ru.mirea.maximister.barbershopbackend.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.mirea.maximister.barbershopbackend.domain.User;
import ru.mirea.maximister.barbershopbackend.dto.users.responses.UserDto;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserToDtoMapper {
    UserDto userToUSerDto(User user);
}
