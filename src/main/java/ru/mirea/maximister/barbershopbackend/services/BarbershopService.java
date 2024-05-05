package ru.mirea.maximister.barbershopbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.maximister.barbershopbackend.domain.Barbershop;
import ru.mirea.maximister.barbershopbackend.dto.barbershop.requests.DeleteBarbershopRequest;
import ru.mirea.maximister.barbershopbackend.dto.barbershop.requests.GetAllBarbershopBarbersRequest;
import ru.mirea.maximister.barbershopbackend.dto.barbershop.requests.SetBarbershopWorkTimeRequest;
import ru.mirea.maximister.barbershopbackend.dto.barbershop.requests.UpdateBarbershopDescriptionRequest;
import ru.mirea.maximister.barbershopbackend.dto.barbershop.responses.BarbershopBarbersResponse;
import ru.mirea.maximister.barbershopbackend.dto.barbershop.responses.BarbershopList;
import ru.mirea.maximister.barbershopbackend.dto.mappers.BarbershopToBarbershopResponseMapper;
import ru.mirea.maximister.barbershopbackend.dto.mappers.UserToResponsesMapper;
import ru.mirea.maximister.barbershopbackend.repository.BarbershopRepository;

import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BarbershopService {
    private final BarbershopRepository barbershopRepository;
    private final BarbershopToBarbershopResponseMapper barbershopMapper;
    private final UserToResponsesMapper userMapper;

    private static final OffsetTime MIN_OPEN_TIME =
            OffsetTime.of(8, 0, 0, 0, ZoneOffset.UTC);
    private static final OffsetTime MAX_OPEN_TIME =
            OffsetTime.of(22, 0, 0, 0, ZoneOffset.UTC);

    @Transactional
    public BarbershopList getAllBarberShops() {
        return new BarbershopList(
                barbershopRepository.findAll().stream()
                        .map(barbershopMapper::barbershopToBarbershopResponse)
                        .collect(Collectors.toList())
        );
    }

    @Transactional
    public BarbershopList getAllBarbershopsByCity(String city) {
        return new BarbershopList(
                barbershopRepository.findBarbershopsByCity(city).stream()
                        .map(barbershopMapper::barbershopToBarbershopResponse)
                        .collect(Collectors.toList())
        );
    }

    @Transactional
    public void deleteBarbershop(DeleteBarbershopRequest request) {
        Barbershop barbershop = getBarbershopByAddress(
                request.city(), request.street(), request.number()
        );

        if (!barbershop.getName().equals(request.name())) {
            log.info("Found invalid barbershop name during deletion: expected '{}', got '{}'",
                    barbershop.getName(),
                    request.name()
            );
            throw new IllegalArgumentException();
        }

        barbershopRepository.deleteById(barbershop.getId());
        log.info("Successfully deleted barbershop {}", request);
    }

    private Barbershop getBarbershopByAddress(String city, String street, String number) {
        return barbershopRepository.getBarbershopByCityAndStreetAndNumber(
                city, street, number
        ).orElseThrow(() -> {
                    log.info("Barbershop {} was not found during removing",
                            city + " " + street + " " + number);
                    //TODO: кастомная ошибка о ненаходе шопа
                    return new IllegalArgumentException("");
                }
        );
    }

    @Transactional
    public void setBarberShopWorkTime(SetBarbershopWorkTimeRequest request) {
        Barbershop barbershop = getBarbershopByAddress(
                request.city(), request.street(), request.number()
        );

        validateBarbershopWorkTime(request.openTime(), request.openTime());

        barbershop.setCloseTime(request.closeTime());
        barbershop.setOpenTime(request.openTime());
        barbershopRepository.save(barbershop);

        log.info("Successfully updated barbershop work time by request: {}", request);
    }

    private void validateBarbershopWorkTime(OffsetTime start, OffsetTime end) {
        if (end.isBefore(start) || end.equals(start)) {
            //TODO: кастомная ошибка задания даты, в описании ошибки описать причину
            log.info("Invalid barbershop work time: from {} to {}",
                    start,
                    end
            );
        }
        if (end.isAfter(MAX_OPEN_TIME)) {
            //TODO: кастомная ошибка задания даты, в описании ошибки описать причину
            log.info("Invalid barbershop work time: from {} to {}",
                    start,
                    end
            );
        }
        if (start.isBefore(MIN_OPEN_TIME)) {
            //TODO: кастомная ошибка задания даты, в описании ошибки описать причину
            log.info("Invalid barbershop work time: from {} to {}",
                    start,
                    end
            );
        }
    }

    @Transactional
    public BarbershopBarbersResponse getAllBarberShopBarbers(GetAllBarbershopBarbersRequest request) {
        Barbershop barbershop = getBarbershopByAddress(
                request.city(), request.street(), request.number()
        );

        return new BarbershopBarbersResponse(
                barbershop.getId(),
                barbershop.getName(),
                barbershopRepository
                        .findAllBarbersByBarbershopId(barbershop.getId())
                        .stream()
                        .map(userMapper::userToBarberResponse)
                        .collect(Collectors.toList())
        );
    }

    public void updateBarberShopDescription(UpdateBarbershopDescriptionRequest request) {
        Barbershop barbershop = getBarbershopByAddress(
                request.city(), request.street(), request.number()
        );

        barbershop.setDescription(request.newDescription());
        barbershopRepository.save(barbershop);
        log.info("Updated barbershop description: {}", request);
    }
}
