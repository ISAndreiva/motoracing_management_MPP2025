package internal.andreiva.concursmotociclism.client;

import internal.andreiva.concursmotociclism.communication.Request;
import internal.andreiva.concursmotociclism.communication.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public abstract class AbstractProxyService
{
    protected final static Logger logger = LogManager.getLogger();
    private final int port;
    private final String host;
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected = false;
    private final BlockingDeque<Response> qresponses = new LinkedBlockingDeque<>();

    public AbstractProxyService(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    public AbstractProxyService()
    {
        this.host = "localhost";
        this.port = 9898;
    }

    public void initConnection()
    {
        logger.info("Starting connection to server {}:{}", host, port);
        try
        {
            socket = new Socket(host, port);
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());
            connected = true;
            logger.info("Connected to server {}:{}", host, port);
            startReader();
        }
        catch (Exception e)
        {
            logger.error("Could not connect to server: {}", e.getMessage());
            throw new RuntimeException("Could not connect to server!");
        }
    }

    private void startReader(){
        var readerThread = new Thread(new ReaderThread());
        readerThread.start();
    }

    private class ReaderThread implements Runnable{
        public void run() {
            logger.info("Starting reader thread");
            while(connected){
                try {
                    var response = input.readObject();
                    logger.info("Received response: {}", response.toString());
                    qresponses.put((Response) response);
                }catch (EOFException ignored)
                {
                }
                catch (Exception e)
                {
                    logger.error(e);
                }
            }
            logger.info("Server stopped");
            if (socket != null)
            {
                try
                {
                    logger.info("Closing server socket");
                    input.close();
                    output.close();
                    socket.close();
                } catch (IOException e)
                {
                    logger.error(e);
                }
            }
        }
    }

    protected void sendRequest(Request request)
    {
        try
        {
            logger.info("Sending request: {}", request.toString());
            output.writeObject(request);
            output.flush();
        }
        catch (IOException e)
        {
            logger.error(e);
        }
    }

    protected Response readResponse()
    {
        Response response = null;
        try
        {
            response = qresponses.take();
        } catch (InterruptedException e)
        {
            logger.error(e);
        }
        return response;
    }

    protected void testConnection() throws IOException
    {
        if (!connected || socket == null || socket.isClosed())
        {
            logger.error("Connection is not open");
            throw new IOException("Connection is not open");
        }
    }

    public void closeConnection()
    {
        logger.info("Closing connection");
        connected = false;
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            logger.error(e);
        }
        try
        {
            output.close();
            input.close();
            socket.close();
            socket = null;
        }
        catch (IOException | NullPointerException e)
        {
            logger.error(e);
        }
    }
}
