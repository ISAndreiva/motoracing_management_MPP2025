package internal.andreiva.concursmotociclism.service;

import internal.andreiva.concursmotociclism.domain.Race;
import internal.andreiva.concursmotociclism.domain.Racer;
import internal.andreiva.concursmotociclism.domain.Team;

import java.util.Set;
import java.util.UUID;

public interface ServiceInterface
{
    boolean checkUserPassword(String username, String password);

    Iterable<Race> getRacesByClass(int raceClass);

    Iterable<Integer> getUsedRaceClasses();

    int getRacersCountForRace(UUID raceId);

    boolean checkUserExists(String username);

    Iterable<Racer> getRacersByTeam(UUID teamId);

    Set<Integer> getRacerClasses(UUID racerId);

    Iterable<Team> getTeamsByPartialName(String name);

    Iterable<Team> getAllTeams();

    void addRacer(Racer racer);

    Iterable<Race> getAllRaces();

    void addRaceRegistration(String racerName, String racerCNP, String teamName, String raceName);
}
