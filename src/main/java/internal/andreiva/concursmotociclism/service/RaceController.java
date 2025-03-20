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

    public Iterable<Integer> getRaceClasses()
    {
        return raceRepository.getRaceClasses();
    }

    public Iterable<Race> getRacesByClass(int raceClass)
    {
        return raceRepository.getRacesByClass(raceClass);
    }
}
