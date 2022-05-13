package ru.omel.rm.views.masterdetail;

import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.omel.rm.data.DataHelper;
import ru.omel.rm.data.dto.PokPuDto;
import ru.omel.rm.data.service.DogService;
import ru.omel.rm.data.service.PokService;
import ru.omel.rm.data.service.PuService;

import javax.annotation.security.PermitAll;
import java.util.List;

@PageTitle("Показания счетчиков")
@Route(value = "/")
//@RouteAlias(value = "")
@PermitAll
@Tag("master-detail-view")
@JsModule("./views/masterdetail/master-detail-view.ts")
public class DtoView extends Div implements HasStyle, BeforeEnterObserver {

    private final String METERS_ID = "metersID";

    // This is the Java companion file of a design
    // You can find the design file inside /frontend/views/
    // The design can be easily edited by using Vaadin Designer
    // (vaadin.com/designer)

    @Id
    private Grid<PokPuDto> grid= new Grid<>(PokPuDto.class);

    private final DogService dogService;
    private final PuService puService;
    private final PokService pokService;

    @Autowired
    public DtoView(DogService dogService
            , PuService puService
            , PokService pokService) {
        this.dogService = dogService;
        this.puService = puService;
        this.pokService = pokService;
        addClassNames("master-detail-view");
        List<PokPuDto> pokPuDtoList = DataHelper.createTable("1-0073",
                dogService, puService, pokService);
//        grid.addColumn("objName").setHeader("Object").setAutoWidth(true);
//        grid.addColumn("objAddress").setHeader("Address").setAutoWidth(true);
//        grid.addColumn("typeDevice").setHeader("Type MD").setAutoWidth(true);
//        grid.addColumn("numDevice").setHeader("Num MD").setAutoWidth(true);
//        grid.addColumn("ratio").setHeader("Ratio").setAutoWidth(true);
//        grid.addColumn("date").setHeader("Date RM").setAutoWidth(true);
//        grid.addColumn("meter").setHeader("Meter").setAutoWidth(true);
        grid.setItems(pokPuDtoList);
        grid.addThemeVariants(GridVariant.LUMO_COMPACT);
        grid.setHeightFull();

        add(grid);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
//        Optional<UUID> valuesMetersId = event.getRouteParameters().get(VALUESMETERS_ID).map(UUID::fromString);
//        if (valuesMetersId.isPresent()) {
//            Optional<ValuesMeters> valuesMetersFromBackend = valuesMetersService.get(valuesMetersId.get());
//            if (valuesMetersFromBackend.isPresent()) {
//                populateForm(valuesMetersFromBackend.get());
//            } else {
//                Notification.show(
//                        String.format("The requested valuesMeters was not found, ID = %s", valuesMetersId.get()), 3000,
//                        Notification.Position.BOTTOM_START);
//                // when a row is selected but the data is no longer available,
//                // refresh grid
                refreshGrid();
//                event.forwardTo(DtoView.class);
//            }
//        }
    }

    private void refreshGrid() {
        grid.select(null);
    }


    private void populateForm() {
//        binder.readBean(this.deviceMeterDto);
    }
}
