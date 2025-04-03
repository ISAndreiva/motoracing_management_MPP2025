package internal.andreiva.concursmotociclism.service;

import internal.andreiva.concursmotociclism.domain.Race;
import internal.andreiva.concursmotociclism.domain.RaceRegistration;
import internal.andreiva.concursmotociclism.domain.Racer;
import internal.andreiva.concursmotociclism.domain.Team;
import internal.andreiva.concursmotociclism.repository.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;
import java.util.UUID;

public class Service implements ServiceInterface
{
    private final UserController userController;
    private final TeamController teamController;
    private final RaceController raceController;
    private final RacerController racerController;
    private final RaceRegistrationController raceRegistrationController;
    protected final static Logger logger = LogManager.getLogger();

    public Service(UserRepositoryInterface userRepository, TeamRepositoryInterface teamRepository, RaceRepositoryInterface raceRepository, RacerRepositoryInterface racerRepository, RaceRegistrationRepositoryInterface raceRegistrationRepository)
    {
        this.userController = new UserController(userRepository);
        this.teamController = new TeamController(teamRepository);
        this.raceController = new RaceController(raceRepository);
        this.racerController = new RacerController(racerRepository);
        this.raceRegistrationController = new RaceRegistrationController(raceRegistrationRepository);
    }

    @Override
    public boolean checkUserPassword(String username, String password)
    {
        logger.info("Checking password for user: {}", username);
        return userController.checkPassword(username, password);
    }

    @Override
    public Iterable<Race> getRacesByClass(int raceClass)
    {
        logger.info("Getting races by class: {}", raceClass);
        return raceController.getRacesByClass(raceClass);
    }

    @Override
    public Iterable<Integer> getUsedRaceClasses()
    {
        logger.info("Getting all used race classes");
        return raceController.getUsedRaceClasses();
    }

    @Override
    public int getRacersCountForRace(UUID raceId)
    {
        logger.info("Getting number of racers registered for race {}", raceId);
        return raceRegistrationController.getNumberOfRacersRegisteredForRace(raceId);
    }

    @Override
    public boolean checkUserExists(String username)
    {
        logger.info("Checking if user exists: {}", username);
        return userController.checkUserExists(username);
    }

    @Override
    public Iterable<Racer> getRacersByTeam(UUID teamId)
    {
        logger.info("Getting racers by team id {}", teamId);
        return racerController.getRacersByTeam(teamId);
    }

    @Override
    public Set<Integer> getRacerClasses(UUID racerId)
    {
        logger.info("Getting racer classes for id {}", racerId);
        return raceRegistrationController.getRacerClasses(racerId);
    }

    @Override
    public Iterable<Team> getTeamsByPartialName(String name)
    {
        logger.info("Getting teams by partial name {}", name);
        return teamController.getTeamsByPartialName(name);
    }

    @Override
    public Iterable<Team> getAllTeams()
    {
        logger.info("Getting all teams");
        return teamController.getAllTeams();
    }

    @Override
    public void addRacer(Racer racer)
    {
        logger.info("Adding racer {} with CNP {}", racer.getName(), racer.getCNP());
        racerController.addRacer(racer);
    }

    @Override
    public Iterable<Race> getAllRaces()
    {
        logger.info("Getting all races");
        return raceController.getAllRaces();
    }

    @Override
    public void addRaceRegistration(String racerName, String racerCNP, String teamName, String raceName)
    {
        logger.info("Adding race registration for {} in race {}", racerName, raceName);
        var racer = racerController.getRacerByCNP(racerCNP);
        if (racer == null)
        {
            racer = new Racer(UUID.randomUUID(), racerName, teamController.getTeamByName(teamName), racerCNP);
            addRacer(racer);
        }
        var race = raceController.getRaceByName(raceName);
        raceRegistrationController.addRaceRegistration(new RaceRegistration(UUID.randomUUID(), race, racer));
    }

}
