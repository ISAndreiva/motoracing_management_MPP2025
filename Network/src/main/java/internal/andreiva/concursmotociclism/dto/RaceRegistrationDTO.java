package internal.andreiva.concursmotociclism.dto;

import internal.andreiva.concursmotociclism.domain.RaceRegistration;

import java.util.UUID;

public class RaceRegistrationDTO extends EntityDTO<UUID>
{
    private final RaceDTO race;
    private final RacerDTO racer;

    public RaceRegistrationDTO(UUID uuid, RaceDTO race, RacerDTO racer)
    {
        super(uuid);
        this.race = race;
        this.racer = racer;
    }

    public RaceDTO getRace()
    {
        return race;
    }

    public RacerDTO getRacer()
    {
        return racer;
    }

    public static RaceRegistrationDTO fromRaceRegistration(RaceRegistration raceRegistration)
    {
        return new RaceRegistrationDTO(raceRegistration.getId(), RaceDTO.fromRace(raceRegistration.getRace()), RacerDTO.fromRacer(raceRegistration.getRacer()));
    }

    public RaceRegistration toRaceRegistration()
    {
        return new RaceRegistration(getId(), race.toRace(), getRacer().toRacer());
    }
}
