package ru.omel.rm.views.admin;

import au.com.bytecode.opencsv.CSVReader;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import ru.omel.rm.data.entity.Dog;
import ru.omel.rm.data.entity.Pok;
import ru.omel.rm.data.entity.Pu;
import ru.omel.rm.data.service.DogService;
import ru.omel.rm.data.service.PokService;
import ru.omel.rm.data.service.PuService;

import javax.annotation.security.PermitAll;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Загрузка данных")
@Route(value = "dbload")
@PermitAll
public class dbload extends Div implements BeforeEnterObserver {
    private final TextField textDog = new TextField();
    private final TextField textPu = new TextField();
    private final TextField textPok = new TextField();

    private final DogService dogService;
    private final PuService puService;
    private final PokService pokService;

    @Autowired
    public dbload(DogService dogService, PuService puService, PokService pokService) {
        this.dogService = dogService;
        this.puService = puService;
        this.pokService = pokService;
        VerticalLayout verticalLayout = new VerticalLayout();
        Label greeting = new Label("Здесь можно загрузить данные в базу");
        Button button = new Button("Загрузить");

        button.addClickListener(e -> {
            try {
                loadData();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        verticalLayout.add(greeting, button, textDog, textPu, textPok);
        add(verticalLayout);
    }

    private void loadData() throws FileNotFoundException {
        loadDog();
        loadPu();
        loadPok();
    }

    private void loadPok() {
        Pok pok = null;
        pokService.deleteAll();
        List<String> strs = new ArrayList<>();
        try {
            File file = new File("\\\\omel1s.omel.corp\\in\\lk_pok.csv");
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "windows-1251");
            //BufferedReader reader = new BufferedReader(fr);
            strs = new BufferedReader(reader)
                    .lines().collect(Collectors.toList());
            for (String s : strs) {
                String[] as = s.split(",");
//            // data,tzona,pdate,vid_en,ce_id,ab_id
//            // 1128.406000,"",05.05.2022,"A+",31217778,2219201
                if(as[0].equals("data")) continue;
                pok = new Pok(as[4].trim()
                        , as[5].trim()
                        , as[3].substring(1,as[3].length()-1).trim()
                        , as[2].trim()
                        , as[1].substring(1,as[1].length()-1).trim()
                        , as[0].trim());
                pokService.update(pok);
            }
            textPok.setValue(strs.get(strs.size()-1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDog() {
        Dog dog = null;
        dogService.deleteAll();
        List<String> strs = new ArrayList<>();
        try {
            File file = new File("\\\\omel1s.omel.corp\\in\\lk_dog.csv");
            FileReader fr = new FileReader(file);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "windows-1251");
            //BufferedReader reader = new BufferedReader(fr);
            strs = new BufferedReader(reader)
                    .lines().collect(Collectors.toList());
            for (String s : strs) {
                String[] as = s.split(",");
                // ab_numgp,ab_num,ab_name,inn,ab_id
                // Dog(String abNum, String abNumgp, String abName, String inn, String abId)
                if(as[0].equals("ab_numgp")) continue;
                dog = new Dog(
                        as[1].substring(1, as[1].length() - 1).trim()
                        , as[0].substring(1, as[0].length() - 1).trim()
                        , as[2].substring(1, as[2].length() - 1).trim()
                        , as[3].trim()
                        , as[4].trim());
                dogService.update(dog);
            }
            textDog.setValue(strs.get(strs.size()-1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPu(){
        Pu pu = null;
        puService.deleteAll();
        List<String> strs = new ArrayList<>();
        try {
            File file = new File("\\\\omel1s.omel.corp\\in\\lk_pu.csv");
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "windows-1251");
            //BufferedReader reader = new BufferedReader(fr);
            strs = new BufferedReader(reader)
                    .lines().collect(Collectors.toList());
            for (String s : strs) {
                String[] as = s.split(",");
                // ob_name,ob_adres,nom_pu,marka,tn,tt,koef,pr_pot,vltl,ce_id,ab_id
                // "Магазин "Цветы"","Центральная 4/2","47866020383720","МИР С-05","-","-",1,0.0000,"СН2",31217996,2219202
                if(as[0].equals("ob_name")) continue;
                pu = new Pu(as[10].trim()
                        , as[9].trim()
                        , as[0].substring(1,as[0].length()-1).trim()
                        , as[1].substring(1,as[1].length()-1).trim()
                        , as[2].substring(1,as[2].length()-1).trim()
                        , as[3].substring(1,as[3].length()-1).trim()
                        , as[4].substring(1,as[4].length()-1).trim()
                        , as[5].substring(1,as[5].length()-1).trim()
                        , as[8].substring(1,as[5].length()-1).trim()
                        , as[6].trim()
                        , as[7].trim());
                puService.update(pu);
            }
            textPu.setValue(strs.get(strs.size()-1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

    }
}
