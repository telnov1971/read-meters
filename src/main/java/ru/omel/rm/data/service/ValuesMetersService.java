package ru.omel.rm.data.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.omel.rm.data.entity.ValuesMeters;

@Service
public class ValuesMetersService {

    private final ValuesMetersRepository repository;

    @Autowired
    public ValuesMetersService(ValuesMetersRepository repository) {
        this.repository = repository;
    }

    public Optional<ValuesMeters> get(UUID id) {
        return repository.findById(id);
    }

    public ValuesMeters update(ValuesMeters entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Page<ValuesMeters> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
