package internal.andreiva.domain;

public class Entity<Id>
{
    private final Id id;

    public Entity(Id id)
    {
        this.id = id;
    }

    public Id getId()
    {
        return id;
    }
}
