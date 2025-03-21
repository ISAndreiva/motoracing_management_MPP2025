package internal.andreiva.concursmotociclism.service;

import internal.andreiva.concursmotociclism.domain.RaceRegistration;
import internal.andreiva.concursmotociclism.repository.RaceRegistrationRepositoryInterface;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class RaceRegistrationController
{
    private final RaceRegistrationRepositoryInterface raceRegistrationRepository;

    public RaceRegistrationController(RaceRegistrationRepositoryInterface raceRegistrationRepository)
    {
        this.raceRegistrationRepository = raceRegistrationRepository;
    }

    public int getNumberOfRacersRegisteredForRace(UUID raceId)
    {
        var res = 0;
        var raceRegistrations = raceRegistrationRepository.getRegistrationsByRace(raceId);
        for (RaceRegistration raceRegistration : raceRegistrations)
        {
            res++;
        }
        return res;
    }

    public Set<Integer> getRacerClasses(UUID racerId)
    {
        var res = new HashSet<Integer>();
        var raceRegistrations = raceRegistrationRepository.getRegistrationsByRacer(racerId);
        for (RaceRegistration raceRegistration : raceRegistrations)
        {
            res.add(raceRegistration.getRace().getRaceClass());
        }
        return res;
    }


}
