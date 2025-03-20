package internal.andreiva.concursmotociclism;

import internal.andreiva.concursmotociclism.gui.GuiViewFactory;
import internal.andreiva.concursmotociclism.repository.db.*;
import internal.andreiva.concursmotociclism.service.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main
{
    public static void main(String[] args)
    {
        var properties = new Properties();
        try
        {
            properties.load(new FileReader("db.properties"));
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        var userDbRepository = new UserDbRepository(properties);
        var teamDbRepository = new TeamDbRepository(properties);
        var raceDbRepository = new RaceDbRepository(properties);
        var racerDbRepository = new RacerDbRepository(properties, teamDbRepository);
        var raceRegistrationDbRepository = new RaceRegistrationDbRepository(properties, racerDbRepository, raceDbRepository);

        var service = new Service(userDbRepository, teamDbRepository, raceDbRepository, racerDbRepository, raceRegistrationDbRepository);
        GuiViewFactory.setService(service);
        GuiViewFactory.launch();

    }
}