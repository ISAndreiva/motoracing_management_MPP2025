package internal.andreiva.domain;

import java.util.Objects;
import java.util.UUID;

public class RaceRegistration extends Entity<UUID>
{
    private final Race race;
    private final Racer racer;
    private final int raceClass;

    public RaceRegistration(UUID uuid, Race race, Racer racer, int raceClass)
    {
        super(uuid);
        this.race = race;
        this.racer = racer;
        this.raceClass = raceClass;
    }

    public Race getRace()
    {
        return race;
    }

    public Racer getRacer()
    {
        return racer;
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
        return raceClass == that.raceClass && Objects.equals(race, that.race) && Objects.equals(racer, that.racer) && Objects.equals(super.getId(), that.getId());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(race, racer, raceClass);
    }
}
