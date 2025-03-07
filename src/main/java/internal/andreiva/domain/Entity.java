package internal.andreiva.domain;

import java.util.UUID;

public class Entity
{
    private final UUID id;

    public Entity(UUID id)
    {
        this.id = id;
    }

    public UUID getId()
    {
        return id;
    }
}
