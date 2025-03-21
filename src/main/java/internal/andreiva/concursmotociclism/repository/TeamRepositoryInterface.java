package internal.andreiva.concursmotociclism.repository;

import internal.andreiva.concursmotociclism.domain.Team;

import java.util.UUID;

public interface TeamRepositoryInterface extends RepositoryInterface<UUID, Team>
{
    Iterable<Team> getTeamsByPartialName(String partialName);

    Team getTeamByName(String name);
}
