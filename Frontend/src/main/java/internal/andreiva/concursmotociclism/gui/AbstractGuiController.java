package internal.andreiva.concursmotociclism.gui;

import internal.andreiva.concursmotociclism.service.AbstractService;

public class AbstractGuiController
{
    protected AbstractService service;
    public void setService(AbstractService service)
    {
        this.service = service;
    }
}
