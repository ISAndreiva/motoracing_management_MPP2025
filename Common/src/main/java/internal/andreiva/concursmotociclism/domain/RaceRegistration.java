package internal.andreiva.concursmotociclism.domain;

import java.util.Objects;
import java.util.UUID;

public class RaceRegistration extends Entity<UUID>
{
    private final Race race;
    private final Racer racer;

    public RaceRegistration(UUID uuid, Race race, Racer racer)
    {
        super(uuid);
        this.race = race;
        this.racer = racer;
    }

    public Race getRace()
    {
        return race;
    }

    public Racer getRacer()
    {
        return racer;
    }


    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass())
            return false;
        RaceRegistration that = (RaceRegistration) o;
        return Objects.equals(race, that.race) && Objects.equals(racer, that.racer) && Objects.equals(super.getId(), that.getId());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(race, racer);
    }
}
