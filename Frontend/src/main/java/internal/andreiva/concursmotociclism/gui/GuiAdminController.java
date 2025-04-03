package internal.andreiva.concursmotociclism.gui;

import internal.andreiva.concursmotociclism.domain.Race;
import internal.andreiva.concursmotociclism.domain.Racer;
import internal.andreiva.concursmotociclism.service.ServiceInterface;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class GuiAdminController extends AbstractGuiController
{
    @FXML
    private TabPane racesTabPane;

    @FXML
    private TableView<Racer> racerTable;

    @FXML
    private TableColumn<Racer, String> racerTableName;

    @FXML
    private TableColumn<Racer, String> racerTableClass;

    @FXML
    private TextField teamSearchField;

    private List<Integer> raceClasses;

    private final List<Stage> childStages = new ArrayList<>();


    @Override
    public void setService(ServiceInterface service)
    {
        super.setService(service);
        createTabs();
        setUpRacerTable();
        setUpSearchField();
    }


    private void setUpSearchField()
    {
        PauseTransition pause = new PauseTransition(Duration.millis(350));
        teamSearchField.textProperty().addListener((observable, oldValue, newValue) ->
        {
            pause.setOnFinished(event -> teamSearchFieldUpdated());
            pause.playFromStart();
        });
    }

    private void setUpRacerTable()
    {
        class racerClassesFactory implements Callback<TableColumn.CellDataFeatures<Racer, String>, ObservableValue<String>>
        {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Racer, String> param)
            {
                var classes = service.getRacerClasses(param.getValue().getId());
                return new SimpleStringProperty(classes.toString());
            }
        }
        racerTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        racerTableClass.setCellValueFactory(new racerClassesFactory());
    }

    private void createTabs()
    {
        var pos = racesTabPane.getSelectionModel().getSelectedIndex();
        raceClasses = StreamSupport.stream(service.getUsedRaceClasses().spliterator(), false).toList();
        racesTabPane.getTabs().clear();
        var tab = new Tab("Toate");
        tab.setContent(createPage(-1));
        racesTabPane.getTabs().add(tab);

        for (var i = 0; i < raceClasses.size(); i++)
        {
            tab = new Tab("Clasa: " + raceClasses.get(i) + "mc");
            tab.setContent(createPage(i));
            racesTabPane.getTabs().add(tab);
        }
        racesTabPane.getSelectionModel().select(pos);
    }

    private Node createPage(int pageIndex)
    {
        class racersNoFactory implements Callback<TableColumn.CellDataFeatures<Race, Integer>, ObservableValue<Integer>>
        {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Race, Integer> param)
            {
                return new SimpleIntegerProperty(service.getRacersCountForRace(param.getValue().getId())).asObject();
            }
        }

        TableView<Race> table = new TableView<>();
        if (pageIndex != -1)
        {
            TableColumn<Race, String> nameColumn = new TableColumn<>("Nume");
            nameColumn.setPrefWidth(285);
            TableColumn<Race, Integer> racersNoColumn = new TableColumn<>("Nr Participanti");
            racersNoColumn.setPrefWidth(285);

            nameColumn.setCellValueFactory(new PropertyValueFactory<>("raceName"));
            racersNoColumn.setCellValueFactory(new racersNoFactory());

            var races = StreamSupport.stream(service.getRacesByClass(raceClasses.get(pageIndex)).spliterator(), false).toList();
            table.getColumns().setAll(List.of(nameColumn, racersNoColumn));
            table.setItems(FXCollections.observableArrayList(races));
        } else
        {
            TableColumn<Race, String> nameColumn = new TableColumn<>("Nume");
            nameColumn.setPrefWidth(190);
            TableColumn<Race, String> classColumn = new TableColumn<>("Clasa");
            classColumn.setPrefWidth(190);
            TableColumn<Race, Integer> racersNoColumn = new TableColumn<>("Nr Participanti");
            racersNoColumn.setPrefWidth(190);

            nameColumn.setCellValueFactory(new PropertyValueFactory<>("raceName"));
            classColumn.setCellValueFactory(new PropertyValueFactory<>("raceClass"));
            racersNoColumn.setCellValueFactory(new racersNoFactory());

            var races = StreamSupport.stream(service.getAllRaces().spliterator(), false).toList();
            table.getColumns().setAll(List.of(nameColumn, classColumn, racersNoColumn));
            table.setItems(FXCollections.observableArrayList(races));
        }

        return table;
    }


    public void teamSearchFieldUpdated()
    {
        if (teamSearchField.getText().isEmpty())
        {
            racerTable.setVisible(false);
            racesTabPane.setVisible(true);
        } else
        {
            racerTable.setVisible(true);
            racesTabPane.setVisible(false);
            var teams = service.getTeamsByPartialName(teamSearchField.getText());
            if (teams == null || !teams.iterator().hasNext())
            {
                racerTable.setItems(FXCollections.observableArrayList());
                return;
            }
            var racers = new ArrayList<Racer>();
            for (var team : teams)
            {
                racers.addAll(StreamSupport.stream(service.getRacersByTeam(team.getId()).spliterator(), false).toList());
            }
            racerTable.setItems(FXCollections.observableArrayList(racers));
        }
    }

    public void handleAddRacer()
    {
        childStages.add(GuiViewFactory.registerView());
    }

    public void handleLogout()
    {
        childStages.forEach(Stage::close);
        var currentStage = (Stage) racesTabPane.getScene().getWindow();
        currentStage.close();
    }

//    @Override
//    public void update(Event event)
//    {
//        if (event.type().equals(EventType.RaceRegistration))
//        {
//            createTabs();
//        }
//    }
}
