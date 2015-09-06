package particlesystem2d.controller;

import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.BeanProperty;
import org.controlsfx.property.BeanPropertyUtils;
import org.controlsfx.property.editor.*;
import org.controlsfx.samples.propertysheet.CustomPropertyDescriptor;
import org.controlsfx.samples.propertysheet.SampleBean;
import particlesystem2d.model.Emitter;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by GFH on 06.09.2015.
 */
public class PropertiesEditorController implements Initializable {

    @FXML
    private BorderPane borderPane;

    private Emitter emitter;

//    public Emitter getEmitter() {
//        return emitter;
//    }
//
//    public void setEmitter(Emitter emitter) {
//        this.emitter = emitter;
//    }

    private PropertySheet propertySheet = new PropertySheet();

    private  Map<String, Object> customDataMap = new LinkedHashMap<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        borderPane.setCenter(propertySheet);

    }


    public void initPropertyShit(Emitter emitter){
        this.emitter = emitter;
        Service<?> service = new Service<ObservableList<PropertySheet.Item>>() {

            @Override

            protected Task<ObservableList<PropertySheet.Item>> createTask() {

                return new Task<ObservableList<PropertySheet.Item>>() {

                    @Override

                    protected ObservableList<PropertySheet.Item> call() throws Exception {

                        return BeanPropertyUtils.getProperties(emitter);

                    }

                };

            }

        };

        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {



            @SuppressWarnings("unchecked")

            @Override

            public void handle(WorkerStateEvent e) {

                propertySheet.getItems().setAll((ObservableList<PropertySheet.Item>) e.getSource().getValue());

            }

        });

        service.start();

    }


}
