package internal.andreiva.concursmotociclism.communication;

import java.io.Serializable;

public class Response implements Serializable
{
    private final ResponseType responseType;
    private final Object data;

    public Response(ResponseType responseType, Object data)
    {
        this.responseType = responseType;
        this.data = data;
    }

    public ResponseType type()
    {
        return responseType;
    }

    public Object data()
    {
        return data;
    }
}
