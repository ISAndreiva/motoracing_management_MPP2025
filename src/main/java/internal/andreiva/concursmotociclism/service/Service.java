package internal.andreiva.concursmotociclism.service;

import internal.andreiva.concursmotociclism.domain.Race;
import internal.andreiva.concursmotociclism.domain.Racer;
import internal.andreiva.concursmotociclism.domain.Team;
import internal.andreiva.concursmotociclism.repository.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;
import java.util.UUID;

public class Service
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

    public boolean checkUserPassword(String username, String password)
    {
        logger.info("Checking password for user: {}", username);
        return userController.checkPassword(username, password);
    }

    public Iterable<Race> getRacesByClass(int raceClass)
    {
        logger.info("Getting races by class: {}", raceClass);
        return raceController.getRacesByClass(raceClass);
    }

    public Iterable<Integer> getRaceClasses()
    {
        logger.info("Getting all race classes");
        return raceController.getRaceClasses();
    }

    public int getRacersCountForRace(UUID raceId)
    {
        logger.info("Getting number of racers registered for race {}", raceId);
        return raceRegistrationController.getNumberOfRacersRegisteredForRace(raceId);
    }

    public boolean checkUserExists(String username)
    {
        logger.info("Checking if user exists: {}", username);
        return userController.checkUserExists(username);
    }

    public Iterable<Racer> getRacersByTeam(UUID teamId)
    {
        logger.info("Getting racers by team id {}", teamId);
        return racerController.getRacersByTeam(teamId);
    }

    public Set<Integer> getRacerClasses(UUID racerId)
    {
        logger.info("Getting racer classes for id {}", racerId);
        return raceRegistrationController.getRacerClasses(racerId);
    }

    public Iterable<Team> getTeamsByPartialName(String name)
    {
        logger.info("Getting teams by partial name {}", name);
        return teamController.getTeamsByPartialName(name);
    }

}
