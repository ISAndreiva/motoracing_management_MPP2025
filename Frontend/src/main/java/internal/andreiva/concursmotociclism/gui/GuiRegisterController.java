package internal.andreiva.concursmotociclism.gui;

import internal.andreiva.concursmotociclism.service.AbstractService;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class GuiRegisterController extends AbstractGuiController
{
    @FXML
    private TextField nameTextField;

    @FXML
    private TextField cnpTextField;

    @FXML
    private ChoiceBox<String> teamComboBox;

    @FXML
    private ChoiceBox<String> raceComboBox;

    @Override
    public void setService(AbstractService service)
    {
        super.setService(service);
        service.getAllTeams().forEach(team -> teamComboBox.getItems().add(team.getName()));
        service.getAllRaces().forEach(race -> raceComboBox.getItems().add(race.getRaceName()));
    }

    public void handleRegisterButton()
    {
        service.addRaceRegistration(nameTextField.getText(), cnpTextField.getText(), teamComboBox.getValue(), raceComboBox.getValue());
    }
}
