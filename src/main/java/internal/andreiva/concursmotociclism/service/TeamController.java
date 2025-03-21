package internal.andreiva.concursmotociclism.service;

import internal.andreiva.concursmotociclism.domain.Team;
import internal.andreiva.concursmotociclism.repository.TeamRepositoryInterface;

public class TeamController
{
    private final TeamRepositoryInterface teamRepository;

    public TeamController(TeamRepositoryInterface teamRepository)
    {
        this.teamRepository = teamRepository;
    }

    public Iterable<Team> getTeamsByPartialName(String partialName)
    {
        return teamRepository.getTeamsByPartialName(partialName);
    }
}
