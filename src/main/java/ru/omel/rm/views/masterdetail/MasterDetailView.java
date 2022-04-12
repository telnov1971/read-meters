package ru.omel.rm.views.masterdetail;

import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import ru.omel.rm.data.entity.ValuesMeters;
import ru.omel.rm.data.service.ValuesMetersService;

@PageTitle("Master-Detail")
@Route(value = "master-detail/:valuesMetersID?/:action?(edit)")
@PermitAll
@Tag("master-detail-view")
@JsModule("./views/masterdetail/master-detail-view.ts")
public class MasterDetailView extends LitTemplate implements HasStyle, BeforeEnterObserver {

    private final String VALUESMETERS_ID = "valuesMetersID";
    private final String VALUESMETERS_EDIT_ROUTE_TEMPLATE = "master-detail/%s/edit";

    // This is the Java companion file of a design
    // You can find the design file inside /frontend/views/
    // The design can be easily edited by using Vaadin Designer
    // (vaadin.com/designer)

    @Id
    private Grid<ValuesMeters> grid;

    @Id
    private TextField num;
    @Id
    private TextField object;
    @Id
    private TextField address;
    @Id
    private TextField typeMD;
    @Id
    private TextField numMD;
    @Id
    private TextField ratio;
    @Id
    private DatePicker dateRM;
    @Id
    private TextField meter;

    @Id
    private Button cancel;
    @Id
    private Button save;

    private BeanValidationBinder<ValuesMeters> binder;

    private ValuesMeters valuesMeters;

    private final ValuesMetersService valuesMetersService;

    @Autowired
    public MasterDetailView(ValuesMetersService valuesMetersService) {
        this.valuesMetersService = valuesMetersService;
        addClassNames("master-detail-view");
        grid.addColumn(ValuesMeters::getNum).setHeader("Num").setAutoWidth(true);
        grid.addColumn(ValuesMeters::getObject).setHeader("Object").setAutoWidth(true);
        grid.addColumn(ValuesMeters::getAddress).setHeader("Address").setAutoWidth(true);
        grid.addColumn(ValuesMeters::getTypeMD).setHeader("Type MD").setAutoWidth(true);
        grid.addColumn(ValuesMeters::getNumMD).setHeader("Num MD").setAutoWidth(true);
        grid.addColumn(ValuesMeters::getRatio).setHeader("Ratio").setAutoWidth(true);
        grid.addColumn(ValuesMeters::getDateRM).setHeader("Date RM").setAutoWidth(true);
        grid.addColumn(ValuesMeters::getMeter).setHeader("Meter").setAutoWidth(true);
        grid.setItems(query -> valuesMetersService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(VALUESMETERS_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(MasterDetailView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(ValuesMeters.class);

        // Bind fields. This is where you'd define e.g. validation rules
        binder.forField(num).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("num");
        binder.forField(numMD).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("numMD");
        binder.forField(ratio).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("ratio");
        binder.forField(meter).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("meter");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.valuesMeters == null) {
                    this.valuesMeters = new ValuesMeters();
                }
                binder.writeBean(this.valuesMeters);

                valuesMetersService.update(this.valuesMeters);
                clearForm();
                refreshGrid();
                Notification.show("ValuesMeters details stored.");
                UI.getCurrent().navigate(MasterDetailView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the valuesMeters details.");
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<UUID> valuesMetersId = event.getRouteParameters().get(VALUESMETERS_ID).map(UUID::fromString);
        if (valuesMetersId.isPresent()) {
            Optional<ValuesMeters> valuesMetersFromBackend = valuesMetersService.get(valuesMetersId.get());
            if (valuesMetersFromBackend.isPresent()) {
                populateForm(valuesMetersFromBackend.get());
            } else {
                Notification.show(
                        String.format("The requested valuesMeters was not found, ID = %s", valuesMetersId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(MasterDetailView.class);
            }
        }
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getLazyDataView().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(ValuesMeters value) {
        this.valuesMeters = value;
        binder.readBean(this.valuesMeters);

    }
}
