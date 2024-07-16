package com.javaweb.repository;

import com.javaweb.entity.RentAreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRentAreaRepository extends JpaRepository<RentAreaEntity, Long>{}