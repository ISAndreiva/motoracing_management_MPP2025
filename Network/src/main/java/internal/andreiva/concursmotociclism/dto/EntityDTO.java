package internal.andreiva.concursmotociclism.dto;

import java.io.Serializable;

public class EntityDTO<Id> implements Serializable
{
    private final Id id;

    public EntityDTO(Id id)
    {
        this.id = id;
    }

    public Id getId()
    {
        return id;
    }
}
