package internal.andreiva.concursmotociclism.service;

import internal.andreiva.concursmotociclism.domain.Race;
import internal.andreiva.concursmotociclism.domain.Racer;
import internal.andreiva.concursmotociclism.domain.Team;
import internal.andreiva.concursmotociclism.utils.Observable;

import java.util.Set;
import java.util.UUID;

public abstract class AbstractService extends Observable
{
    public abstract boolean checkUserPassword(String username, String password);

    public abstract Iterable<Race> getRacesByClass(int raceClass);

    public abstract Iterable<Integer> getUsedRaceClasses();

    public abstract int getRacersCountForRace(UUID raceId);

    public abstract boolean checkUserExists(String username);

    public abstract Iterable<Racer> getRacersByTeam(UUID teamId);

    public abstract Set<Integer> getRacerClasses(UUID racerId);

    public abstract Iterable<Team> getTeamsByPartialName(String name);

    public abstract Iterable<Team> getAllTeams();

    public abstract void addRacer(Racer racer);

    public abstract Iterable<Race> getAllRaces();

    public abstract void addRaceRegistration(String racerName, String racerCNP, String teamName, String raceName);
}
