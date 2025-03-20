package internal.andreiva.concursmotociclism.service;

import internal.andreiva.concursmotociclism.repository.RaceRegistrationRepositoryInterface;

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
        var registrations = raceRegistrationRepository.getRegistrationsByRace(raceId);
        while (registrations.iterator().hasNext())
        {
            res++;
        }
        return res;
    }
}
