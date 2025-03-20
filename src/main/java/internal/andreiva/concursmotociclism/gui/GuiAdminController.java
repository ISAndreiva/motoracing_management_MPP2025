package internal.andreiva.concursmotociclism.gui;

import internal.andreiva.concursmotociclism.domain.Race;
import internal.andreiva.concursmotociclism.service.Service;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.List;
import java.util.stream.StreamSupport;

public class GuiAdminController extends AbstractGuiController<Object>
{
    @FXML
    private Pagination racesPagination;

    private List<Integer> raceClasses;

    @Override
    public void setService(Service service)
    {
        super.setService(service);
        createPages();
    }

    private void createPages()
    {
        raceClasses =  StreamSupport.stream(service.getRaceClasses().spliterator(), false).toList();
        racesPagination.setPageCount(raceClasses.size());
        racesPagination.setPageFactory(this::createPage);
    }

    private class racersNoFactory implements Callback<TableColumn.CellDataFeatures<Race, Integer>, ObservableValue<Integer>> {
        @Override
        public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Race, Integer> param) {
            return new SimpleIntegerProperty(service.getRacersCountForRace(param.getValue().getId())).asObject();
        }
    }

    private Node createPage(int pageIndex)
    {
        var box = new VBox();
        var label = new Label("Race class: " + raceClasses.get(pageIndex));
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
}
