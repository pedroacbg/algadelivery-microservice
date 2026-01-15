package com.pedroacbg.delivery.tracking.domain.model;

import com.pedroacbg.delivery.tracking.domain.exception.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryTest {

    @Test
    public void shouldChangeToPlaced(){
        Delivery delivery = Delivery.draft();
        delivery.editPreparationDetails(createValidPreparationDetails());
        delivery.place();

        Assertions.assertEquals(DeliveryStatus.WAITING_FOR_COURIER, delivery.getStatus());
        Assertions.assertNotNull(delivery.getPlacedAt());
    }

    @Test
    public void shouldNotChangeToPlaced(){
        Delivery delivery = Delivery.draft();

        Assertions.assertThrows(DomainException.class, () -> {
            delivery.place();
        });
        Assertions.assertEquals(DeliveryStatus.DRAFT, delivery.getStatus());
        Assertions.assertNull(delivery.getPlacedAt());
    }

    private Delivery.PreparationDetails createValidPreparationDetails() {
        ContactPoint sender = ContactPoint.builder()
                .zipCode("00000-000")
                .street("Rua Miguel Azedo")
                .number("67")
                .complement("Apto. 30")
                .name("Jonas Aguiar")
                .phone("(45) 98765-3321")
                .build();

        ContactPoint recipient = ContactPoint.builder()
                .zipCode("03400-000")
                .street("Rua Jefferson Azedo")
                .number("27")
                .complement("Apto. 502")
                .name("Jasper Wellington")
                .phone("(45) 98435-3321")
                .build();

       return Delivery.PreparationDetails.builder()
                .sender(sender)
                .recipient(recipient)
                .distanceFee(new BigDecimal("15.00"))
                .courierPayout(new BigDecimal("5.00"))
                .expectedDeliveryTime(Duration.ofDays(5))
                .build();
    }

}