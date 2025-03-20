package internal.andreiva.concursmotociclism.repository;

import internal.andreiva.concursmotociclism.domain.Race;

import java.util.UUID;

public interface RaceRepositoryInterface extends RepositoryInterface<UUID, Race>
{
    Iterable<Race> getRacesByClass(int raceClass);

    Iterable<Integer> getRaceClasses();
}
