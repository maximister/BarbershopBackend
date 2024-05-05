package ru.mirea.maximister.barbershopbackend.dto.mappers;

import org.mapstruct.Mapper;
import ru.mirea.maximister.barbershopbackend.domain.Barbershop;
import ru.mirea.maximister.barbershopbackend.dto.barbershop.responses.BarbershopResponse;

@Mapper
public interface BarbershopToBarbershopResponseMapper {
    BarbershopResponse barbershopToBarbershopResponse(Barbershop barbershop);
}
