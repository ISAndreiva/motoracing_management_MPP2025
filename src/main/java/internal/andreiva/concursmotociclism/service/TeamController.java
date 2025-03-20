package internal.andreiva.concursmotociclism.service;

import internal.andreiva.concursmotociclism.repository.TeamRepositoryInterface;

public class TeamController
{
    private final TeamRepositoryInterface teamRepository;

    public TeamController(TeamRepositoryInterface teamRepository)
    {
        this.teamRepository = teamRepository;
    }
}
