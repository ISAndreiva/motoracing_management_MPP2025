package internal.andreiva.concursmotociclism.gui;

import internal.andreiva.concursmotociclism.service.ServiceInterface;

public class AbstractGuiController
{
    protected ServiceInterface service;
    public void setService(ServiceInterface service)
    {
        this.service = service;
    }
}
