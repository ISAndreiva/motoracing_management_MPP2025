package internal.andreiva.concursmotociclism.dto;

import internal.andreiva.concursmotociclism.domain.Entity;
import internal.andreiva.concursmotociclism.domain.Team;

import java.util.UUID;

public class TeamDTO extends EntityDTO<UUID>
{
    private final String name;

    public TeamDTO(UUID id, String name)
    {
        super(id);
        this.name = name;
    }

    public static TeamDTO fromTeam(Team team)
    {
        return new TeamDTO(team.getId(), team.getName());
    }

    public Team toTeam()
    {
        return new Team(getId(), name);
    }
}
