package internal.andreiva.concursmotociclism.dto;

import internal.andreiva.concursmotociclism.domain.Racer;

import java.util.UUID;

public class RacerDTO extends EntityDTO<UUID>
{
    private final String name;
    private final TeamDTO team;
    private final String CNP;

    public RacerDTO(UUID id, String name, TeamDTO team, String CNP)
    {
        super(id);
        this.name = name;
        this.team = team;
        this.CNP = CNP;
    }



    public static RacerDTO fromRacer(Racer racer)
    {
        return new RacerDTO(racer.getId(), racer.getName(), TeamDTO.fromTeam(racer.getTeam()), racer.getCNP());
    }

    public Racer toRacer()
    {
        return new Racer(getId(), name, team.toTeam(), CNP);
    }
}
