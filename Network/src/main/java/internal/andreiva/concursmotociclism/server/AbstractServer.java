package internal.andreiva.concursmotociclism.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.ServerSocket;
import java.net.Socket;

public abstract class AbstractServer
{
    protected final static Logger logger = LogManager.getLogger();
    private final int port;
    private ServerSocket serverSocket = null;

    public AbstractServer(int port)
    {
        this.port = port;
    }

    public AbstractServer()
    {
        this.port = 9898;
    }

    public void start()
    {
        logger.info("Starting server...");
        try
        {
            logger.info("Trying to bind port {}...", port);
            serverSocket = new ServerSocket(port);
            logger.info("Server started!");
            while (true)
            {
                logger.info("Waiting for clients ...");
                var client = serverSocket.accept();
                logger.info("Client connected!");
                handleClient(client);
            }
        }
        catch (Exception e)
        {
            logger.error(e);
        }
        finally
        {
            if (serverSocket != null)
                stop();
        }
    }

    protected abstract void handleClient(Socket client);

    public void stop()
    {
        logger.info("Stopping server...");
        try
        {
            serverSocket.close();
            logger.info("Server stopped!");
        }
        catch (Exception e)
        {
            logger.error(e);
        }
    }

}
