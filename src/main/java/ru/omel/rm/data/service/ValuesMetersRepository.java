package ru.omel.rm.data.service;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.omel.rm.data.entity.ValuesMeters;

public interface ValuesMetersRepository extends JpaRepository<ValuesMeters, UUID> {

}