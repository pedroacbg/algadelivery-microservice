package com.pedroacbg.courier.management.domain.repository;

import com.pedroacbg.courier.management.domain.model.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourierRespository extends JpaRepository<Courier, UUID> {
}
