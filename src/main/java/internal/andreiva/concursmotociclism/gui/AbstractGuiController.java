package internal.andreiva.concursmotociclism.gui;

import internal.andreiva.concursmotociclism.service.Service;

public class AbstractGuiController
{
    protected Service service;
    public void setService(Service service)
    {
        this.service = service;
    }
}
