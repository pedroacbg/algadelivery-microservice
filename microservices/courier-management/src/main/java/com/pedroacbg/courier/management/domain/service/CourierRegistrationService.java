package com.pedroacbg.courier.management.domain.service;

import com.pedroacbg.courier.management.api.model.CourierInput;
import com.pedroacbg.courier.management.domain.model.Courier;
import com.pedroacbg.courier.management.domain.repository.CourierRespository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CourierRegistrationService {

    private final CourierRespository courierRespository;

    public Courier create(@Valid CourierInput request) {
        Courier courier = Courier.brandNew(request.getName(), request.getPhone());
        return courierRespository.saveAndFlush(courier);
    }

    public Courier update(UUID courierId, @Valid CourierInput request) {
        Courier courier = courierRespository.findById(courierId).orElseThrow();
        courier.setName(request.getName());
        courier.setPhone(request.getPhone());
        return courierRespository.saveAndFlush(courier);
    }
}
