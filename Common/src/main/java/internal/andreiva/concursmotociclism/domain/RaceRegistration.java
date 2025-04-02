package internal.andreiva.concursmotociclism.domain;

import java.util.Objects;
import java.util.UUID;

public class RaceRegistration extends internal.andreiva.concursmotociclism.domain.Entity<UUID>
{
    private final internal.andreiva.concursmotociclism.domain.Race race;
    private final Racer racer;

    public RaceRegistration(UUID uuid, internal.andreiva.concursmotociclism.domain.Race race, internal.andreiva.concursmotociclism.domain.Racer racer)
    {
        super(uuid);
        this.race = race;
        this.racer = racer;
    }

    public internal.andreiva.concursmotociclism.domain.Race getRace()
    {
        return race;
    }

    public internal.andreiva.concursmotociclism.domain.Racer getRacer()
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
