package ru.omel.rm.data.service;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.omel.rm.data.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);
}