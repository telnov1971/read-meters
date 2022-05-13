package ru.omel.rm.data;

import ru.omel.rm.data.dto.PokPuDto;
import ru.omel.rm.data.entity.Dog;
import ru.omel.rm.data.entity.Pok;
import ru.omel.rm.data.entity.Pu;
import ru.omel.rm.data.service.*;

import java.util.ArrayList;
import java.util.List;

public class DataHelper {
    public static List<PokPuDto> createTable(String num
            , DogService dogService
            , PuService puService
            , PokService pokService) {
        Dog dog;
        List<PokPuDto> result = new ArrayList<>();

        if(dogService.findByAbNum(num).isPresent()) {
            dog = dogService.findByAbNum(num).get();
            List<Pu> pus = puService.findAllByAbId(dog.getAbId());
            for(Pu pu : pus) {
                List<Pok> poks = pokService.findAllByCeId(pu.getCeId());
                for(Pok pok : poks) {
                    PokPuDto pokPuDto = new PokPuDto(
                            pu.getObName()
                            , pu.getObAdres()
                            , pu.getMarka()
                            , pu.getNomPu()
                            , pu.getKoef()
                            , pok.getPdate()
                            , pok.getData()
                    );
                    result.add(pokPuDto);
                }
            }
        }
        return result;
    }
}
