package ru.omel.rm.data.generator;

import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.omel.rm.data.Role;
import ru.omel.rm.data.entity.User;
import ru.omel.rm.data.entity.ValuesMeters;
import ru.omel.rm.data.service.UserRepository;
import ru.omel.rm.data.service.ValuesMetersRepository;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(PasswordEncoder passwordEncoder, UserRepository userRepository,
            ValuesMetersRepository valuesMetersRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (userRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");

            logger.info("... generating 2 User entities...");
            User user = new User();
            user.setName("John Normal");
            user.setUsername("user");
            user.setHashedPassword(passwordEncoder.encode("user"));
            user.setProfilePictureUrl(
                    "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
            user.setRoles(Collections.singleton(Role.USER));
            userRepository.save(user);
            User admin = new User();
            admin.setName("Emma Powerful");
            admin.setUsername("admin");
            admin.setHashedPassword(passwordEncoder.encode("admin"));
            admin.setProfilePictureUrl(
                    "https://images.unsplash.com/photo-1607746882042-944635dfe10e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
            admin.setRoles(Set.of(Role.USER, Role.ADMIN));
            userRepository.save(admin);
            logger.info("... generating 100 Values Meters entities...");
            ExampleDataGenerator<ValuesMeters> valuesMetersRepositoryGenerator = new ExampleDataGenerator<>(
                    ValuesMeters.class, LocalDateTime.of(2022, 4, 12, 0, 0, 0));
            valuesMetersRepositoryGenerator.setData(ValuesMeters::setNum, DataType.NUMBER_UP_TO_100);
            valuesMetersRepositoryGenerator.setData(ValuesMeters::setObject, DataType.FULL_NAME);
            valuesMetersRepositoryGenerator.setData(ValuesMeters::setAddress, DataType.ADDRESS);
            valuesMetersRepositoryGenerator.setData(ValuesMeters::setTypeMD, DataType.WORD);
            valuesMetersRepositoryGenerator.setData(ValuesMeters::setNumMD, DataType.NUMBER_UP_TO_1000);
            valuesMetersRepositoryGenerator.setData(ValuesMeters::setRatio, DataType.NUMBER_UP_TO_100);
            valuesMetersRepositoryGenerator.setData(ValuesMeters::setDateRM, DataType.DATE_OF_BIRTH);
            valuesMetersRepositoryGenerator.setData(ValuesMeters::setMeter, DataType.NUMBER_UP_TO_1000);
            valuesMetersRepository.saveAll(valuesMetersRepositoryGenerator.create(100, seed));

            logger.info("Generated demo data");
        };
    }

}