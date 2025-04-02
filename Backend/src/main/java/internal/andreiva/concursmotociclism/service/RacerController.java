package internal.andreiva.concursmotociclism.service;

import internal.andreiva.concursmotociclism.domain.Racer;
import internal.andreiva.concursmotociclism.repository.RacerRepositoryInterface;

import java.util.UUID;

public class RacerController
{
    private final RacerRepositoryInterface racerRepository;

    public RacerController(RacerRepositoryInterface racerRepository)
    {
        this.racerRepository = racerRepository;
    }

    public Iterable<Racer> getRacersByTeam(UUID teamId)
    {
        return racerRepository.getRacersByTeam(teamId);
    }

    public void addRacer(Racer racer)
    {
        racerRepository.add(racer);
    }

    public Racer getRacerByCNP(String cnp)
    {
        return racerRepository.getRacerByCNP(cnp);
    }
}
