package internal.andreiva.concursmotociclism.service;

import internal.andreiva.concursmotociclism.domain.Race;
import internal.andreiva.concursmotociclism.repository.RaceRepositoryInterface;

public class RaceController
{
    private final RaceRepositoryInterface raceRepository;

    public RaceController(RaceRepositoryInterface raceRepository)
    {
        this.raceRepository = raceRepository;
    }

    public Iterable<Integer> getUsedRaceClasses()
    {
        return raceRepository.getUsedRaceClasses();
    }

    public Iterable<Race> getRacesByClass(int raceClass)
    {
        return raceRepository.getRacesByClass(raceClass);
    }

    public Iterable<Race> getAllRaces()
    {
        return raceRepository.getAll();
    }

    public Race getRaceByName(String name)
    {
        return raceRepository.getRaceByName(name);
    }
}
