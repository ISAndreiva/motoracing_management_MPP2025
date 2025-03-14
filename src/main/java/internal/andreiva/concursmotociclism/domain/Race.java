package internal.andreiva.concursmotociclism.domain;

import java.util.Objects;
import java.util.UUID;

public class Race extends internal.andreiva.concursmotociclism.domain.Entity<UUID>
{
    private final int raceClass;
    private final String raceName;

    public Race(UUID id, int raceClass, String raceName)
    {
        super(id);
        this.raceClass = raceClass;
        this.raceName = raceName;
    }

    public int getRaceClass()
    {
        return raceClass;
    }

    public String getRaceName()
    {
        return raceName;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass())
            return false;
        Race race = (Race) o;
        return raceClass == race.raceClass && Objects.equals(raceName, race.raceName) && Objects.equals(super.getId(), race.getId());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(raceClass, raceName);
    }
}
