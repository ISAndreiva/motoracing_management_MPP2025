package internal.andreiva.concursmotociclism.utils;

import java.io.Serializable;

public record Event(EventType type, Object data) implements Serializable
{
}
