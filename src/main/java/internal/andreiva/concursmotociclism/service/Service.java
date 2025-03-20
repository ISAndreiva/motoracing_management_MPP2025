package internal.andreiva.concursmotociclism.service;

import internal.andreiva.concursmotociclism.domain.Race;
import internal.andreiva.concursmotociclism.repository.*;

import java.util.UUID;

public class Service
{
    private final UserController userController;
    private final TeamController teamController;
    private final RaceController raceController;
    private final RacerController racerController;
    private final RaceRegistrationController raceRegistrationController;

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
        return userController.checkPassword(username, password);
    }

    public Iterable<Race> getRacesByClass(int raceClass)
    {
        return raceController.getRacesByClass(raceClass);
    }

    public Iterable<Integer> getRaceClasses()
    {
        return raceController.getRaceClasses();
    }

    public int getRacersCountForRace(UUID raceId)
    {
        return raceRegistrationController.getNumberOfRacersRegisteredForRace(raceId);
    }

}
