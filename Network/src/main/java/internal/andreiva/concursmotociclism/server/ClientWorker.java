package internal.andreiva.concursmotociclism.server;


import internal.andreiva.concursmotociclism.communication.Request;
import internal.andreiva.concursmotociclism.communication.Response;
import internal.andreiva.concursmotociclism.communication.ResponseType;
import internal.andreiva.concursmotociclism.domain.Race;
import internal.andreiva.concursmotociclism.dto.*;
import internal.andreiva.concursmotociclism.service.ServiceInterface;
import internal.andreiva.concursmotociclism.utils.Event;
import internal.andreiva.concursmotociclism.utils.EventType;
import internal.andreiva.concursmotociclism.utils.Observer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.StreamSupport;


public class ClientWorker implements Runnable, Observer
{
    protected final static Logger logger = LogManager.getLogger();
    private final ServiceInterface service;
    private final Socket socket;

    private final ObjectInputStream input;
    private final ObjectOutputStream output;
    private volatile boolean connected =  false;

    public ClientWorker(ServiceInterface service, Socket socket)
    {
        this.service = service;
        if (service instanceof ObservableServiceWrapper)
            ((ObservableServiceWrapper) service).registerObserver(this);

        this.socket = socket;
        ObjectInputStream inputTemp = null;
        ObjectOutputStream outputTemp = null;
        try
        {
            inputTemp = new ObjectInputStream(socket.getInputStream());
            outputTemp = new ObjectOutputStream(socket.getOutputStream());
            outputTemp.flush();
            connected = true;
        } catch (IOException e)
        {
            logger.error(e);
        }
        input = inputTemp;
        output = outputTemp;
    }

    @Override
    public void run()
    {
        while(connected)
        {
            try
            {
                Request request = (Request) input.readObject();
                logger.debug("Received request: {}", request);
                Response response = handleRequest(request);
                logger.debug("Sending response: {}", response);
                sendResponse(response);
            }
            catch (EOFException e) {
                break;
            }
            catch (IOException e)
            {
                logger.error(e);
                connected = false;
            } catch (ClassNotFoundException e)
            {
                logger.error(e);
            }
        }
        logger.info("Client disconnected");
        try
        {
            Thread.sleep(1000);
        } catch (InterruptedException e)
        {
            logger.error(e);
        }
        try
        {
            input.close();
            output.close();
            socket.close();
            if (service instanceof ObservableServiceWrapper)
                ((ObservableServiceWrapper) service).unregisterObserver(this);
        } catch (Exception e)
        {
            logger.error("Oh no, failed to close socket on exit, anyway. Here is why: ", e);
        }
    }

    private Response handleRequest(Request request)
    {
        Response response = null;
        var function = "handle"+request.type();
        try
        {
            var method = this.getClass().getDeclaredMethod(function, request.getClass());
            logger.debug("Received request of type: {} and handled with method {}", request.type(), function);
            response = (Response) method.invoke(this, request);
        } catch (Exception e)
        {
            logger.error(e);
        }

        if (response == null)
        {
            logger.error("Response is null");
            response = new Response(ResponseType.Error, "Request could not be handled");
        }
        return response;
    }

    private void sendResponse(Response response)
    {
        try
        {
            synchronized (output)
            {
                output.writeObject(response);
                output.flush();
            }
        } catch (IOException e)
        {
            logger.error(e);
        }
    }

    private Response handleCheckUserPassword(Request request)
    {
        var crediantials = (UserCrediantialsDTO) request.data();
        if (service.checkUserPassword(crediantials.username(), crediantials.password()))
        {
            return new Response(ResponseType.Ok, null);
        }
        else
        {
            return new Response(ResponseType.Error, null);
        }
    }

    private Response handleGetRacesByClass(Request request)
    {
        try
        {
            var raceClass = (Integer) request.data();
            var races = new ArrayList<RaceDTO>();
            service.getRacesByClass(raceClass).forEach(r -> races.add(RaceDTO.fromRace(r)));
            return new Response(ResponseType.GetRacesByClass, races);
        } catch (Exception e)
        {
            logger.error(e);
            return new Response(ResponseType.Error, e.getMessage());
        }
    }

    private Response handleGetUsedRaceClasses(Request request)
    {
        try
        {
            var classes = StreamSupport.stream(service.getUsedRaceClasses().spliterator(), false).toList();
            return new Response(ResponseType.GetUsedRaceClasses, classes);
        } catch (Exception e)
        {
            logger.error(e);
            return new Response(ResponseType.Error, e.getMessage());
        }
    }

    private Response handleGetRacersCountForRace(Request request)
    {
        try
        {
            var raceId = (UUID) request.data();
            var count = service.getRacersCountForRace(raceId);
            return new Response(ResponseType.GetRacersCountForRace, count);
        } catch (Exception e)
        {
            logger.error(e);
            return new Response(ResponseType.Error, e.getMessage());
        }
    }

    private Response handleCheckUserExists(Request request)
    {
        var username = (String) request.data();
        if (service.checkUserExists(username))
        {
            return new Response(ResponseType.Ok, null);
        }
        else
        {
            return new Response(ResponseType.Error, null);
        }
    }

    private Response handleGetRacersByTeam(Request request)
    {
        try
        {
            var teamId = (UUID) request.data();
            var racers = new ArrayList<RacerDTO>();
            service.getRacersByTeam(teamId).forEach(r -> racers.add(RacerDTO.fromRacer(r)));
            return new Response(ResponseType.GetRacersByTeam, racers);
        } catch (Exception e)
        {
            logger.error(e);
            return new Response(ResponseType.Error, e.getMessage());
        }
    }

    private Response handleGetRacerClasses(Request request)
    {
        try
        {
            var racerId = (UUID) request.data();
            var classes = service.getRacerClasses(racerId);
            return new Response(ResponseType.GetRacerClasses, classes);
        }
        catch (Exception e)
        {
            logger.error(e);
            return new Response(ResponseType.Error, e.getMessage());
        }
    }

    private Response handleGetTeamsByPartialName(Request request)
    {
        try
        {
            var name = (String) request.data();
            var teams = new ArrayList<TeamDTO>();
            service.getTeamsByPartialName(name).forEach(t -> teams.add(TeamDTO.fromTeam(t)));
            return new Response(ResponseType.GetTeamsByPartialName, teams);
        } catch (Exception e)
        {
            logger.error(e);
            return new Response(ResponseType.Error, e.getMessage());
        }
    }

    private Response handleGetAllTeams(Request request)
    {
        try
        {
            var teams = new ArrayList<TeamDTO>();
            service.getAllTeams().forEach(t -> teams.add(TeamDTO.fromTeam(t)));
            return new Response(ResponseType.GetAllTeams, teams);
        } catch (Exception e)
        {
            logger.error(e);
            return new Response(ResponseType.Error, e.getMessage());
        }
    }

    private Response handleAddRacer(Request request)
    {
        try
        {
            var racer = (RacerDTO) request.data();
            service.addRacer(racer.toRacer());
            return new Response(ResponseType.Ok, null);
        } catch (Exception e)
        {
            logger.error(e);
            return new Response(ResponseType.Error, e.getMessage());
        }
    }

    private Response handleGetAllRaces(Request request)
    {
        try
        {
            var races = new ArrayList<RaceDTO>();
            service.getAllRaces().forEach(t -> races.add(RaceDTO.fromRace(t)));
            return new Response(ResponseType.GetAllRaces, races);
        } catch (Exception e)
        {
            logger.error(e);
            return new Response(ResponseType.Error, e.getMessage());
        }
    }

    private Response handleAddRaceRegistration(Request request)
    {
        try
        {
            var raceRegistration = (RaceRegistrationDTO) request.data();
            service.addRaceRegistration(raceRegistration.racerName(), raceRegistration.racerCNP(),
                    raceRegistration.teamName(), raceRegistration.raceName());
            return new Response(ResponseType.Ok, null);
        } catch (Exception e)
        {
            logger.error(e);
            return new Response(ResponseType.Error, e.getMessage());
        }
    }

    private Response handleGetRaceByName(Request request)
    {
        try
        {
            var raceName = (String) request.data();
            var race = service.getRaceByName(raceName);
            if (race == null)
                return new Response(ResponseType.Error, null);
            return new Response(ResponseType.GetRaceByName, RaceDTO.fromRace(race));
        } catch (Exception e)
        {
            logger.error(e);
            return new Response(ResponseType.Error, e.getMessage());
        }
    }

    @Override
    public void update(EventType type, Object data)
    {
        try
        {
            logger.info("Sending update event");
            sendResponse(new Response(ResponseType.Update, new Event(type, RaceDTO.fromRace((Race) data))));
        } catch (Exception e)
        {
            logger.error(e);
        }
    }
}
