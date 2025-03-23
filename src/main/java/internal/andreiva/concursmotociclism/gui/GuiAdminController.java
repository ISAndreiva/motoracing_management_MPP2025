package internal.andreiva.concursmotociclism.gui;

import internal.andreiva.concursmotociclism.domain.Race;
import internal.andreiva.concursmotociclism.domain.Racer;
import internal.andreiva.concursmotociclism.service.Service;
import internal.andreiva.concursmotociclism.utils.Event;
import internal.andreiva.concursmotociclism.utils.EventType;
import internal.andreiva.concursmotociclism.utils.Observer;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class GuiAdminController extends AbstractGuiController<Object> implements Observer
{
    @FXML
    private Pagination racesPagination;

    @FXML
    private TableView<Racer> racerTable;

    @FXML
    private TableColumn<Racer, String> racerTableName;

    @FXML
    private TableColumn<Racer, String> racerTableClass;

    @FXML
    private TextField teamSearchField;

    private List<Integer> raceClasses;

    private List<Stage> childStages = new ArrayList<>();


    @Override
    public void setService(Service service)
    {
        super.setService(service);
        service.addObserver(this);
        createPages();
        setUpRacerTable();
        setUpSearchField();
    }

    @Override
    public void update(Event event)
    {
        if (event.type() == EventType.RaceRegistration)
        {
            createPages();
            racerTable.getItems().clear();
            teamSearchFieldUpdated();
        }
    }

    private void setUpSearchField()
    {
        PauseTransition pause = new PauseTransition(Duration.millis(350));
        teamSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            pause.setOnFinished(event -> teamSearchFieldUpdated());
            pause.playFromStart();
        });
    }

    private void setUpRacerTable()
    {
        class racerClassesFactory implements Callback<TableColumn.CellDataFeatures<Racer, String>, ObservableValue<String>> {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Racer, String> param) {
                var classes = service.getRacerClasses(param.getValue().getId());
                return new SimpleStringProperty(classes.toString());
            }
        }
        racerTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        racerTableClass.setCellValueFactory(new racerClassesFactory());
    }

    private void createPages()
    {
        var index = racesPagination.getCurrentPageIndex();
        raceClasses = StreamSupport.stream(service.getUsedRaceClasses().spliterator(), false).toList();
        racesPagination.setPageCount(raceClasses.size());
        racesPagination.setPageFactory(this::createPage);
        racesPagination.setCurrentPageIndex(index);
    }

    private Node createPage(int pageIndex)
    {
        class racersNoFactory implements Callback<TableColumn.CellDataFeatures<Race, Integer>, ObservableValue<Integer>> {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Race, Integer> param) {
                return new SimpleIntegerProperty(service.getRacersCountForRace(param.getValue().getId())).asObject();
            }
        }

        var box = new VBox();
        var label = new Label("Clasa: " + raceClasses.get(pageIndex) + "mc");
        label.setStyle("-fx-alignment: center; -fx-font-size: 20px; -fx-font-weight: bold");
        box.setAlignment(javafx.geometry.Pos.CENTER);

        TableView<Race> table = new TableView<>();
        TableColumn<Race, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setPrefWidth(285);
        TableColumn<Race, Integer> racersNoColumn = new TableColumn<>("Participants");
        racersNoColumn.setPrefWidth(285);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("raceName"));
        racersNoColumn.setCellValueFactory(new racersNoFactory());

        var races = StreamSupport.stream(service.getRacesByClass(raceClasses.get(pageIndex)).spliterator(), false).toList();
        table.getColumns().setAll(List.of(nameColumn, racersNoColumn));
        table.setItems(FXCollections.observableArrayList(races));

        box.getChildren().addAll(label, table);

        return box;
    }


    public void teamSearchFieldUpdated()
    {
        if (teamSearchField.getText().isEmpty())
        {
            racerTable.setVisible(false);
            racesPagination.setVisible(true);
        }
        else
        {
            racerTable.setVisible(true);
            racesPagination.setVisible(false);
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
        service.removeObserver(this);
        childStages.forEach(Stage::close);
        var currentStage = (Stage) racesPagination.getScene().getWindow();
        currentStage.close();
    }
}
