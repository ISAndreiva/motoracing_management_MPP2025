package internal.andreiva.concursmotociclism.communication;

import java.io.Serializable;

public class Request implements Serializable
{
    private final RequestType requestType;
    private final Object data;

    public Request(RequestType requestType, Object data)
    {
        this.requestType = requestType;
        this.data = data;
    }

    public RequestType type()
    {
        return requestType;
    }

    public Object data()
    {
        return data;
    }

    @Override
    public String toString()
    {
        return "Request{" +
                "requestType=" + requestType +
                ", data=" + data +
                '}';
    }
}
