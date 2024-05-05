package ru.mirea.maximister.barbershopbackend.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.mirea.maximister.barbershopbackend.domain.User;
import ru.mirea.maximister.barbershopbackend.dto.users.responses.AdminResponse;
import ru.mirea.maximister.barbershopbackend.dto.users.responses.BarberResponse;
import ru.mirea.maximister.barbershopbackend.dto.users.responses.ClientResponse;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserToResponsesMapper {
    BarberResponse userToBarberResponse(User user);
    ClientResponse userToClientResponse(User user);
    AdminResponse userToAdminResponse(User user);
}
