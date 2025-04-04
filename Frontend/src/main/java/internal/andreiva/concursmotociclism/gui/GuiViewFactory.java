package internal.andreiva.concursmotociclism.gui;

import internal.andreiva.concursmotociclism.service.ServiceInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class GuiViewFactory extends Application
{
    private static ServiceInterface service;
    private static Stage stage;
    protected final static Logger logger = LogManager.getLogger();
    public static void setService(ServiceInterface service)
    {
        GuiViewFactory.service = service;
    }

    public static void launch()
    {
        Application.launch();
    }

    @Override
    public void start(Stage stage)
    {
        GuiViewFactory.stage = stage;
        loginView();
        stage.setResizable(false);
        stage.show();
    }

    public static void loginView()
    {
        stage.setScene(createScene("concursmotociclism/gui/loginView.fxml", null));
        stage.setTitle("Concurs Motociclism");
        stage.show();
    }

    public static void adminView()
    {
        Stage stage = new Stage();
        stage.setScene(createScene("concursmotociclism/gui/adminView.fxml", null));
        stage.setTitle("Concurs Motociclism");
        stage.show();
    }

    public static Stage registerView()
    {
        Stage stage = new Stage();
        stage.setScene(createScene("concursmotociclism/gui/registerView.fxml", null));
        stage.setTitle("Concurs Motociclism");
        stage.show();
        return stage;
    }

    private static Scene createScene(String fxml, String css)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(GuiViewFactory.class.getClassLoader().getResource(fxml));
        try
        {
            AnchorPane root = fxmlLoader.load();
            AbstractGuiController controller = fxmlLoader.getController();
            controller.setService(service);
            double scaleValue = 1.25;
            Scale scale = new Scale(scaleValue, scaleValue);
            root.getTransforms().add(scale);
            root.setPrefHeight(root.getPrefHeight()*scaleValue);
            root.setPrefWidth(root.getPrefWidth()*scaleValue);
            Scene scene = new Scene(root);
            if (css != null)
            {
                scene.getStylesheets().add(GuiViewFactory.class.getClassLoader().getResource(css).toExternalForm());
            }
            return scene;
        }
        catch (IOException e)
        {
            logger.error(e);
            return null;
        }
    }
}
