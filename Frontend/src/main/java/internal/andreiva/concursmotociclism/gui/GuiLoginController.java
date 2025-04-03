package internal.andreiva.concursmotociclism.gui;

import internal.andreiva.concursmotociclism.utils.PasswordHasher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class GuiLoginController extends AbstractGuiController
{
    @FXML
    TextField usernameTextField;

    @FXML
    PasswordField passwordTextField;

    @FXML
    Label errorLabel;

    public void handleLogin(ActionEvent actionEvent)
    {
        try
        {
            if (!usernameTextField.getText().isEmpty())
            {
                if (service.checkUserExists(usernameTextField.getText()))
                {
                    if (service.checkUserPassword(usernameTextField.getText(), PasswordHasher.hashPassword(passwordTextField.getText(), usernameTextField.getText())))
                        GuiViewFactory.adminView();
                    else
                    {
                        errorLabel.setText("Username or password is wrong!");
                        errorLabel.setStyle("-fx-text-fill: red");
                    }
                } else
                {
                    errorLabel.setText("Username or password is wrong!");
                    errorLabel.setStyle("-fx-text-fill: red");
                }
            }
        } catch (Exception e)
        {
            System.out.println(e);
            errorLabel.setText(e.getMessage());
        }
    }
}

