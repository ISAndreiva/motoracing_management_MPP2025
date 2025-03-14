package internal.andreiva.concursmotociclism.repository;

import internal.andreiva.concursmotociclism.domain.Racer;

import java.util.UUID;

public interface RacerRepositoryInterface extends RepositoryInterface<UUID, Racer>
{
    Iterable<Racer> getRacersByTeam(UUID teamId);
}
