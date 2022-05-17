package ru.omel.rm.data.generator;

import com.vaadin.flow.spring.annotation.SpringComponent;

import java.util.Collections;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.omel.rm.data.Role;
import ru.omel.rm.data.entity.*;
import ru.omel.rm.data.service.*;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(PasswordEncoder passwordEncoder
            , UserRepository userRepository
            , DogRepository dogRepository
            , PuRepository puRepository
            , PokRepository pokRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (dogRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }

            String str = "\"Hello \"World\"\"";
            str.substring(0,0);
            str.substring(1,str.length()-2);

            Dog dog = new Dog("1-0073"
                    ,"55100001910073"
                    ,"ООО \"Юнилевер Русь\""
                    ,"7705183476"
                    ,"2100077");
            dogRepository.save(dog);

            Pu device = new Pu("2100077"
                    ,"31217228"
                    , "Киоск \"Искра\""
                    , "Кошевого 25"
                    , "47866020339626"
                    ,"МИР С-05"
                    ,"-"
                    ,"-"
                    ,"НН"
                    ,"1"
                    ,"0.0000"
            );
            puRepository.save(device);

            Pok pok = new Pok("31217228"
                    , "2100077"
                    , "A+"
                    , "2022.05.05"
                    ,""
                    ,"9805.658000"
            );
            pokRepository.save(pok);

//            contract = new Contract("55100001910141"
//                    ," 1-0141"
//                    ,"ООО \"СМУ-4 КПД\""
//                    ,5501253717L
//                    ,2100143);
//            contractRepository.save(contract);


//            final String pathFileContract = "C:\\Dev\\src\\java\\lk_dog.csv";
//            final String pathFileDevice = "lk_pu.csv";
//            final String pathFileMeter = "lk_pok.csv";
//
//            File contractFile = new File(pathFileContract);
//            File deviceFile = new File(pathFileDevice);
//            File meterFile = new File(pathFileMeter);
//
//            if(contractFile.exists()){
//                try {
//                    if(contractFile.exists()){
//                        List<String> strs = new ArrayList<>();
//                        Scanner sc = new Scanner(contractFile);
//                        if(sc.hasNextLine()) sc.nextLine();
//                        while(sc.hasNextLine()){
//                            strs.add(sc.nextLine());
//                        }
//                        for(String s : strs) {
//                            String[] as = s.split(",");
//                            System.out.print("| ");
//                            for(String a : as){
//                                System.out.print(s);
//                                System.out.print(" | ");
//                            }
//                            System.out.print(" |");
//                        }
//                        sc.close();
//                    }
//                } catch (IOException e) {
//                }
//            }
//
            int seed = 123;

            logger.info("Loading data...");

            logger.info("... generating 2 User entities...");

            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user"));
            user.setRoles(Collections.singleton(Role.USER));
            user.setChangePassword(false);
            userRepository.save(user);
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRoles(Set.of(Role.USER, Role.ADMIN));
            admin.setChangePassword(false);
            userRepository.save(admin);
            logger.info("Loaded data");
        };
    }

}