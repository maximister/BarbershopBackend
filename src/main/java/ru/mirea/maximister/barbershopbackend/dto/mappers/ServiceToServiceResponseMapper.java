package ru.mirea.maximister.barbershopbackend.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.mirea.maximister.barbershopbackend.domain.Service;
import ru.mirea.maximister.barbershopbackend.dto.service.Response.ServiceResponse;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ServiceToServiceResponseMapper {
    ServiceResponse serviceToServiceResponse(Service service);
}
