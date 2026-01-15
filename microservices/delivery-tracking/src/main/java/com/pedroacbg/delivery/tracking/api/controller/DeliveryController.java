package com.pedroacbg.delivery.tracking.api.controller;

import com.pedroacbg.delivery.tracking.api.model.DeliveryInput;
import com.pedroacbg.delivery.tracking.domain.model.Delivery;
import com.pedroacbg.delivery.tracking.domain.repository.DeliveryRepository;
import com.pedroacbg.delivery.tracking.domain.service.DeliveryPreparationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryPreparationService deliveryPreparationService;
    private final DeliveryRepository deliveryRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Delivery draft(@RequestBody @Valid DeliveryInput request){
        return deliveryPreparationService.draft(request);
    }

    @PutMapping("/{deliveryId}")
    public Delivery edit(@PathVariable UUID deliveryId, @RequestBody @Valid DeliveryInput request){
        return deliveryPreparationService.edit(deliveryId, request);
    }

    @GetMapping
    public PagedModel<Delivery> findAll(@PageableDefault Pageable pageable){
        return new PagedModel<>(deliveryRepository.findAll(pageable));
    }

    @GetMapping("/{deliveryId}")
    public Delivery findById(@PathVariable UUID deliveryId){
        return deliveryRepository.findById(deliveryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    

}
