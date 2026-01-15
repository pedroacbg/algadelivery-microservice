package com.pedroacbg.delivery.tracking.repository;

import com.pedroacbg.delivery.tracking.domain.model.ContactPoint;
import com.pedroacbg.delivery.tracking.domain.model.Delivery;
import com.pedroacbg.delivery.tracking.domain.repository.DeliveryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DeliveryRepositoryTest {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Test
    public void shouldPersist(){
        Delivery delivery = Delivery.draft();
        delivery.editPreparationDetails(createValidPreparationDetails());

        delivery.addItem("Computador", 2);
        delivery.addItem("Celular", 2);

        deliveryRepository.saveAndFlush(delivery);

        Delivery persistedDelivery = deliveryRepository.findById(delivery.getId()).orElseThrow();

        assertEquals(2, persistedDelivery.getItems().size());
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