package internal.andreiva.concursmotociclism.dto;


import internal.andreiva.concursmotociclism.domain.Race;

import java.util.UUID;

public class RaceDTO extends EntityDTO<UUID>
{
    private final int raceClass;
    private final String raceName;

    public RaceDTO(UUID id, int raceClass, String raceName)
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

    public static RaceDTO fromRace(Race race)
    {
        return new RaceDTO(race.getId(), race.getRaceClass(), race.getRaceName());
    }

    public Race toRace()
    {
        return new Race(getId(), getRaceClass(), getRaceName());
    }
}
