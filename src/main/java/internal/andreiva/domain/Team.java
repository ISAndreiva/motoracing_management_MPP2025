package internal.andreiva.domain;

import java.util.Objects;
import java.util.UUID;

public class Team extends Entity<UUID>
{
    private final String name;

    public Team(UUID id, String name)
    {
        super(id);
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass())
            return false;
        Team team = (Team) o;
        return Objects.equals(name, team.name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(name);
    }
}
