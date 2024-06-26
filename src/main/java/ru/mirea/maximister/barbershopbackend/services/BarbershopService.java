package ru.mirea.maximister.barbershopbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.maximister.barbershopbackend.domain.Barbershop;
import ru.mirea.maximister.barbershopbackend.domain.User;
import ru.mirea.maximister.barbershopbackend.dto.barbershop.requests.*;
import ru.mirea.maximister.barbershopbackend.dto.barbershop.responses.BarbershopBarbersResponse;
import ru.mirea.maximister.barbershopbackend.dto.barbershop.responses.BarbershopList;
import ru.mirea.maximister.barbershopbackend.mappers.BarbershopToBarbershopDtoMapper;
import ru.mirea.maximister.barbershopbackend.mappers.UserToDtoMapper;
import ru.mirea.maximister.barbershopbackend.exceptions.*;
import ru.mirea.maximister.barbershopbackend.repository.BarbershopRepository;
import ru.mirea.maximister.barbershopbackend.repository.ServiceRepository;
import ru.mirea.maximister.barbershopbackend.repository.UserRepository;

import java.time.DateTimeException;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BarbershopService {
    private final BarbershopRepository barbershopRepository;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private final BarbershopToBarbershopDtoMapper barbershopMapper;
    private final UserToDtoMapper userMapper;
    private static final OffsetTime MIN_OPEN_TIME =
            OffsetTime.of(8, 0, 0, 0, ZoneOffset.UTC);
    private static final OffsetTime MAX_OPEN_TIME =
            OffsetTime.of(22, 0, 0, 0, ZoneOffset.UTC);

    @Transactional
    public void addBarbershop(AddBarbershopRequest request) {
        Barbershop barbershop = Barbershop.builder()
                .name(request.name())
                .description(request.description())
                .city(request.city())
                .street(request.street())
                .number(request.number())
                .postalCode(request.postalCode())
                .longitude(request.longitude())
                .latitude(request.latitude())
                .build();

        if (barbershopRepository.getBarbershopByCityAndStreetAndNumber(
                        barbershop.getCity(), barbershop.getStreet(), barbershop.getNumber())
                .isPresent()) {
            log.info("Barbershop with address {} is already exists", barbershop.getAddress());
            throw new BarbershopAlreadyExistsException(barbershop.getAddress());
        }
        barbershopRepository.save(barbershop);
        log.info("Barbershop with address {} was successfully registered", barbershop.getAddress());
    }

    @Transactional
    public BarbershopList getAllBarberShops() {
        return new BarbershopList(
                barbershopRepository.findAll().stream()
                        .map(barbershopMapper::barbershopToBarbershopDto)
                        .collect(Collectors.toList())
        );
    }

    @Transactional
    public BarbershopList getAllBarbershopsByCity(String city) {
        return new BarbershopList(
                barbershopRepository.findBarbershopsByCity(city).stream()
                        .map(barbershopMapper::barbershopToBarbershopDto)
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
            throw new BarbershopException("Found invalid barbershop name during deletion");
        }

        barbershopRepository.deleteById(barbershop.getId());
        log.info("Successfully deleted barbershop {}", request);
    }

    public Barbershop getBarbershopByAddress(String city, String street, String number) {
        return barbershopRepository.getBarbershopByCityAndStreetAndNumber(
                city, street, number
        ).orElseThrow(() -> {
                    log.info("Barbershop {} was not found during removing",
                            city + " " + street + " " + number);
                    return new BarbershopNotFoundException(city + " " + street + " " + number);
                }
        );
    }

    @Transactional
    public void setBarberShopWorkTime(SetBarbershopWorkTimeRequest request) {
        Barbershop barbershop = getBarbershopByAddress(
                request.city(), request.street(), request.number()
        );

        validateBarbershopWorkTime(request.openTime(), request.closeTime());

        barbershop.setCloseTime(request.closeTime());
        barbershop.setOpenTime(request.openTime());
        //barbershopRepository.save(barbershop);

        log.info("Successfully updated barbershop work time by request: {}", request);
    }

    private void validateBarbershopWorkTime(OffsetTime start, OffsetTime end) {
        if (end.isBefore(start) || end.equals(start) || end.isAfter(MAX_OPEN_TIME)
                || start.isBefore(MIN_OPEN_TIME)) {
            log.info("Invalid barbershop work time: from {} to {}",
                    start,
                    end
            );
            throw new DateTimeException("Invalid barbershop work time");
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
                        .map(userMapper::userToUSerDto)
                        .collect(Collectors.toList())
        );
    }

    @Transactional
    public void updateBarberShopDescription(UpdateBarbershopDescriptionRequest request) {
        Barbershop barbershop = getBarbershopByAddress(
                request.city(), request.street(), request.number()
        );

        barbershop.setDescription(request.newDescription());
        barbershopRepository.save(barbershop);
        log.info("Updated barbershop description: {}", request);
    }

    public void updateBarbershop(Barbershop barbershop) {
        barbershopRepository.save(barbershop);
    }

    @Transactional
    public void addServiceToBarbershop(AddServiceToBarbershopRequest request) {
        Barbershop barbershop = getBarbershopByAddress(
                request.city(), request.street(), request.number()
        );

        ru.mirea.maximister.barbershopbackend.domain.Service service
                = serviceRepository.findByName(request.serviceName())
                .orElseThrow(() -> {
                    log.info("no such service {}", request.serviceName());
                    return new ServiceNotFoundException(request.serviceName());
                });

        if (barbershop.getServices().contains(service)) {
            throw new ServiceAlreadyExistsException("Barbershop " + barbershop.getAddress() +
                    " already has service " + service.getName());
        }

        barbershop.addService(service);
        log.info("Service {} was added to barbershop {}", service.getName(), barbershop.getAddress());
    }

    @Transactional
    public void deleteBarbershopService(DeleteBarbershopServiceRequest request) {
        Barbershop barbershop = getBarbershopByAddress(request.city(), request.street(), request.number());
        ru.mirea.maximister.barbershopbackend.domain.Service service
                = serviceRepository.findByName(request.serviceName())
                .orElseThrow(() -> {
                    log.info("No such service {}", request.serviceName());
                    return new ServiceNotFoundException(request.serviceName());
                });

        barbershop.deleteService(service);

        for (User barber : barbershop.getBarbers()) {
            barber.deleteService(service);
            userRepository.save(barber);
        }

        barbershopRepository.save(barbershop);
    }
}
