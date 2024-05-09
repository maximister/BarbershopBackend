package ru.mirea.maximister.barbershopbackend.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.mirea.maximister.barbershopbackend.domain.Barbershop;
import ru.mirea.maximister.barbershopbackend.dto.barbershop.responses.BarbershopDto;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BarbershopToBarbershopDtoMapper {
    BarbershopDto barbershopToBarbershopDto(Barbershop barbershop);
}
