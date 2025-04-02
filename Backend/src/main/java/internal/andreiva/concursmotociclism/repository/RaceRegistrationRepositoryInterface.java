package internal.andreiva.concursmotociclism.repository;

import internal.andreiva.concursmotociclism.domain.RaceRegistration;

import java.util.UUID;

public interface RaceRegistrationRepositoryInterface extends RepositoryInterface<UUID, RaceRegistration>
{
    Iterable<RaceRegistration> getRegistrationsByRace(UUID raceId);
    Iterable<RaceRegistration> getRegistrationsByRacer(UUID racerId);
}
