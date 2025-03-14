package internal.andreiva.domain;

import java.util.Objects;
import java.util.UUID;

public class Racer extends Entity<UUID>
{
    private final String name;
    private Team team;
    private final String CNP;

    public Racer(UUID id, String name, Team team, String CNP)
    {
        super(id);
        this.name = name;
        this.team = team;
        this.CNP = CNP;
    }

    public String getName()
    {
        return name;
    }

    public Team getTeam()
    {
        return team;
    }

    public String getCNP()
    {
        return CNP;
    }

    public void setTeam(Team team)
    {
        this.team = team;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass())
            return false;
        Racer racer = (Racer) o;
        return Objects.equals(name, racer.name) && Objects.equals(team, racer.team) && Objects.equals(CNP, racer.CNP) && Objects.equals(super.getId(), racer.getId());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, team, CNP);
    }
}
