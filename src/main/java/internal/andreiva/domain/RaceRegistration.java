package internal.andreiva.domain;

import java.util.Objects;
import java.util.UUID;

public class RaceRegistration extends Entity<UUID>
{
    private final UUID raceId;
    private final UUID racerId;
    private final int raceClass;

    public RaceRegistration(UUID id, UUID raceId, UUID racerId, int raceClass)
    {
        super(id);
        this.raceId = raceId;
        this.racerId = racerId;
        this.raceClass = raceClass;
    }

    public UUID getRaceId()
    {
        return raceId;
    }

    public UUID getRacerId()
    {
        return racerId;
    }

    public int getRaceClass()
    {
        return raceClass;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass())
            return false;
        RaceRegistration that = (RaceRegistration) o;
        return raceClass == that.raceClass && Objects.equals(raceId, that.raceId) && Objects.equals(racerId, that.racerId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(raceId, racerId, raceClass);
    }
}
