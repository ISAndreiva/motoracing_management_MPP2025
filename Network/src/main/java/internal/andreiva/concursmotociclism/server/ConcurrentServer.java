package internal.andreiva.concursmotociclism.server;

import internal.andreiva.concursmotociclism.service.ServiceInterface;

import java.net.Socket;

public class ConcurrentServer extends AbstractServer
{
    private final ServiceInterface service;

    public ConcurrentServer(int port, ServiceInterface service)
    {
        super(port);
        this.service = service;
    }

    public ConcurrentServer(ServiceInterface service)
    {
        super();
        this.service = service;
    }

    @Override
    protected void handleClient(Socket client)
    {
        var thread = new Thread(new ClientWorker(service, client));
        thread.start();
    }
}
