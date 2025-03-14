package internal.andreiva.concursmotociclism;

import internal.andreiva.concursmotociclism.repository.db.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

public class Main
{
    public static void main(String[] args)
    {
        var properties = new Properties();
        try
        {
            properties.load(new FileReader("src/main/resources/concursmotociclism/db.properties"));
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }

//        var user1 = new User(UUID.randomUUID(), "Andreiva", "password_hash");
//        var user2 = new User(UUID.randomUUID(), "Andrei", "password_hash");
//        var Team1 = new Team(UUID.randomUUID(), "Team1");
//        var Team2 = new Team(UUID.randomUUID(), "Team2");
//        var Race1 = new Race(UUID.randomUUID(), 250, "Race1");
//        var Race2 = new Race(UUID.randomUUID(), 1000, "Race2");
//        var Racer1 = new Racer(UUID.randomUUID(), "Racer1", Team1, "0123456789111");
//        var Racer2 = new Racer(UUID.randomUUID(), "Racer2", Team2, "0123456789222");
//        var raceRegistration1 = new RaceRegistration(UUID.randomUUID(), Race1, Racer1, Race1.getRaceClass());
//        var raceRegistration2 = new RaceRegistration(UUID.randomUUID(), Race2, Racer2, Race2.getRaceClass());

        var userDbRepository = new UserDbRepository(properties);
        var teamDbRepository = new TeamDbRepository(properties);
        var raceDbRepository = new RaceDbRepository(properties);
        var racerDbRepository = new RacerDbRepository(properties);
        var raceRegistrationDbRepository = new RaceRegistrationDbRepository(properties);

//        userDbRepository.add(user1);
//        userDbRepository.add(user2);
//        teamDbRepository.add(Team1);
//        teamDbRepository.add(Team2);
//        raceDbRepository.add(Race1);
//        raceDbRepository.add(Race2);
//        racerDbRepository.add(Racer1);
//        racerDbRepository.add(Racer2);
//        raceRegistrationDbRepository.add(raceRegistration1);
//        raceRegistrationDbRepository.add(raceRegistration2);

        var users = userDbRepository.getAll();
        var teams = teamDbRepository.getAll();

        System.out.println("Teams:");
        for (var e : racerDbRepository.getRacersByTeam(UUID.fromString("2c1cb029-c538-4ca6-be6d-e7473ab1818f")))
        {
            System.out.println(e.getName());
            System.out.println(e.getTeam().getName());
        }





    }
}