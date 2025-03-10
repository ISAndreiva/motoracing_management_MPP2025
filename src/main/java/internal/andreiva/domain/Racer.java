package internal.andreiva.domain;

import java.util.Objects;
import java.util.UUID;

public class Racer extends Entity<UUID>
{
    private final String name;
    private UUID teamId;
    private final String CNP;

    public Racer(UUID id, String name, UUID teamId, String CNP)
    {
        super(id);
        this.name = name;
        this.teamId = teamId;
        this.CNP = CNP;
    }

    public String getName()
    {
        return name;
    }

    public UUID getTeamId()
    {
        return teamId;
    }

    public String getCNP()
    {
        return CNP;
    }

    public void setTeamId(UUID teamId)
    {
        this.teamId = teamId;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass())
            return false;
        Racer racer = (Racer) o;
        return Objects.equals(name, racer.name) && Objects.equals(teamId, racer.teamId) && Objects.equals(CNP, racer.CNP);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, teamId, CNP);
    }
}
