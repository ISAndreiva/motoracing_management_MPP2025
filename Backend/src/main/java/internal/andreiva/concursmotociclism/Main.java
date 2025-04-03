package internal.andreiva.concursmotociclism;

import internal.andreiva.concursmotociclism.repository.db.*;
import internal.andreiva.concursmotociclism.server.AbstractServer;
import internal.andreiva.concursmotociclism.server.ConcurrentServer;
import internal.andreiva.concursmotociclism.service.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class Main
{
    private static AbstractServer server = null;

    public static void main(String[] args)
    {
        var properties = new Properties();
        try
        {
            properties.load(new FileReader("server.properties"));
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

        if (!properties.getProperty("server.port").isEmpty())
        {
            server = new ConcurrentServer((Integer.parseInt(properties.getProperty("server.port"))), service);
        }
        else
        {
            server = new ConcurrentServer(service);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() ->
        {
                server.stop();
        }));
        server.start();
    }
}