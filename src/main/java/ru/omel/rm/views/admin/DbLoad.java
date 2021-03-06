package ru.omel.rm.views.admin;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.omel.rm.data.entity.*;
import ru.omel.rm.data.service.*;
import ru.omel.rm.views.main.MainView;

import javax.annotation.security.PermitAll;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Загрузка данных")
@Route(value = "dbload", layout = MainView.class)
@PermitAll
public class DbLoad extends Div implements BeforeEnterObserver {
    private final TextField textDog = new TextField("Договоров:");
    private final TextField textPu = new TextField("Счётчиков:");
    private final TextField textPok = new TextField("Показаний:");

    private final DogService dogService;
    private final PuService puService;
    private final PokService pokService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final LastService lastService;

    private Last last = new Last();

    @Autowired
    public DbLoad(DogService dogService
            , PuService puService
            , PokService pokService
            , UserService userService
            , PasswordEncoder passwordEncoder
            , LastService lastService) {
        this.dogService = dogService;
        this.puService = puService;
        this.pokService = pokService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.lastService = lastService;
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
        if(lastService.getLast(1L).isPresent()){
            last = lastService.getLast(1L).get();
        } else {
            lastService.update(last);
        }
        loadDog();
        loadPu();
        loadPok();
        generateUsers();
    }

    private void loadPok() {
        Pok pok;
        pokService.deleteAll();
        List<String> strs;
        try {
            File file = new File("\\\\omel1s.omel.corp\\in\\lk_pok.csv");
            if(last.getDatePok() < file.lastModified()) {
                InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "windows-1251");
                //BufferedReader reader = new BufferedReader(fr);
                strs = new BufferedReader(reader)
                        .lines().collect(Collectors.toList());
                for (String s : strs) {
                    String[] as = s.split(";");
//            // data,tzona,pdate,vid_en,ce_id,ab_id
//            // 1128.406000,"",05.05.2022,"A+",31217778,2219201
                    if (as[0].equals("data")) continue;
                    pok = new Pok(as[4].trim()
                            , as[5].trim()
                            , as[3].substring(1, as[3].length() - 1).trim()
                            , as[2].trim()
                            , as[1].substring(1, as[1].length() - 1).trim()
                            , as[0].trim());
                    pokService.update(pok);
                }
                last.setDatePok(file.lastModified());
                lastService.update(last);
                textPok.setValue(String.valueOf(strs.size()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDog() {
        Dog dog;
        dogService.deleteAll();
        List<String> strs;
        try {
            File file = new File("\\\\omel1s.omel.corp\\in\\lk_dog.csv");
            if(last.getDateDog() < file.lastModified()) {
                InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "windows-1251");
                //BufferedReader reader = new BufferedReader(fr);
                strs = new BufferedReader(reader)
                        .lines().collect(Collectors.toList());
                for (String s : strs) {
                    String[] as = s.split(";");
                    // ab_numgp,ab_num,ab_name,inn,ab_id
                    // Dog(String abNum, String abNumgp, String abName, String inn, String abId)
                    if (as[0].equals("ab_numgp")) continue;
                    dog = new Dog(
                            as[1].substring(1, as[1].length() - 1).trim()
                            , as[0].substring(1, as[0].length() - 1).trim()
                            , as[2].substring(1, as[2].length() - 1).trim()
                            , as[3].trim()
                            , as[4].trim());
                    dogService.update(dog);
                }
                last.setDateDog(file.lastModified());
                lastService.update(last);
                textDog.setValue(String.valueOf(strs.size()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPu(){
        Pu pu;
        puService.deleteAll();
        List<String> strs;
        try {
            File file = new File("\\\\omel1s.omel.corp\\in\\lk_pu.csv");
            if(last.getDatePu() < file.lastModified()) {
                InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "windows-1251");
                //BufferedReader reader = new BufferedReader(fr);
                strs = new BufferedReader(reader)
                        .lines().collect(Collectors.toList());
                for (String s : strs) {
                    String[] as = s.split(";");
                    // 0 ob_name 3
                    // ,1 ob_adres 4
                    // ,2 nom_pu 5
                    // ,3 marka 6
                    // ,4 tn 7
                    // ,5 tt 8
                    // ,6 koef 10
                    // ,7 pr_pot 11
                    // ,8 vltl 9
                    // ,9 ce_id 2
                    // ,10 ab_id 1
                    // "Магазин "Цветы"","Центральная 4/2","47866020383720","МИР С-05","-","-",1,0.0000,"СН2",31217996,2219202

                    if (as[0].equals("ob_name")) continue;
                    pu = new Pu(as[10].trim()
                            , as[9].trim()
                            , as[0].substring(1, as[0].length() - 1).trim()
                            , as[1].substring(1, as[1].length() - 1).trim()
                            , as[2].substring(1, as[2].length() - 1).trim()
                            , as[3].substring(1, as[3].length() - 1).trim()
                            , as[4].substring(1, as[4].length() - 1).trim()
                            , as[5].substring(1, as[5].length() - 1).trim()
                            , as[8].substring(1, as[8].length() - 1).trim()
                            , as[6].trim()
                            , as[7].trim());
                    puService.update(pu);
                }
                last.setDatePu(file.lastModified());
                lastService.update(last);
                textPu.setValue(String.valueOf(strs.size()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateUsers(){
        User user;
        String name;
        List<Dog> dogs = dogService.findAll();
        for (Dog dog : dogs) {
            name = dog.getAbNum();
            user = userService.findByUsername(name);
            if(user == null) {
                user = new User();
                user.setUsername(name);
                user.setPassword(this.passwordEncoder.encode(revers(name)));
                user.setChangePassword(true);
                userService.update(user);
            }
        }
    }

    private String revers(String str) {
        StringBuilder newStr = new StringBuilder();
        int tail = str.length();
        for(int index = 0; index < tail; index++) {
            newStr.append(str, tail - index - 1, tail - index);
        }
        return newStr.toString();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

    }
}
