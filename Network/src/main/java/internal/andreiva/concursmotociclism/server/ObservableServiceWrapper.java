package internal.andreiva.concursmotociclism.server;

import internal.andreiva.concursmotociclism.domain.Race;
import internal.andreiva.concursmotociclism.domain.Racer;
import internal.andreiva.concursmotociclism.domain.Team;
import internal.andreiva.concursmotociclism.service.ServiceInterface;
import internal.andreiva.concursmotociclism.utils.EventType;
import internal.andreiva.concursmotociclism.utils.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ObservableServiceWrapper implements ServiceInterface
{
    private final ServiceInterface service;
    private final List<Observer> observers = new ArrayList<>();

    public ObservableServiceWrapper(ServiceInterface wrappedService)
    {
        this.service = wrappedService;
    }

    public void registerObserver(Observer observer)
    {
        observers.add(observer);
    }

    public void unregisterObserver(Observer observer)
    {
        observers.remove(observer);
    }

    @Override
    public boolean checkUserPassword(String username, String password)
    {
        return service.checkUserPassword(username, password);
    }

    @Override
    public Iterable<Race> getRacesByClass(int raceClass)
    {
        return service.getRacesByClass(raceClass);
    }

    @Override
    public Iterable<Integer> getUsedRaceClasses()
    {
        return service.getUsedRaceClasses();
    }

    @Override
    public int getRacersCountForRace(UUID raceId)
    {
        return service.getRacersCountForRace(raceId);
    }

    @Override
    public boolean checkUserExists(String username)
    {
        return service.checkUserExists(username);
    }

    @Override
    public Iterable<Racer> getRacersByTeam(UUID teamId)
    {
        return service.getRacersByTeam(teamId);
    }

    @Override
    public Set<Integer> getRacerClasses(UUID racerId)
    {
        return service.getRacerClasses(racerId);
    }

    @Override
    public Iterable<Team> getTeamsByPartialName(String name)
    {
        return service.getTeamsByPartialName(name);
    }

    @Override
    public Iterable<Team> getAllTeams()
    {
        return service.getAllTeams();
    }

    @Override
    public void addRacer(Racer racer)
    {
        service.addRacer(racer);
    }

    @Override
    public Iterable<Race> getAllRaces()
    {
        return service.getAllRaces();
    }

    @Override
    public void addRaceRegistration(String racerName, String racerCNP, String teamName, String raceName)
    {
        service.addRaceRegistration(racerName, racerCNP, teamName, raceName);
        for (var observer : observers)
        {
            observer.update(EventType.RaceRegistration, service.getRaceByName(raceName));
        }
    }

    @Override
    public Race getRaceByName(String raceName)
    {
        return service.getRaceByName(raceName);
    }
}
