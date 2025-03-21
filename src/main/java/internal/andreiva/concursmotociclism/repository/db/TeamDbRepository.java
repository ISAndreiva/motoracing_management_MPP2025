package internal.andreiva.concursmotociclism.repository.db;

import internal.andreiva.concursmotociclism.domain.Team;
import internal.andreiva.concursmotociclism.repository.TeamRepositoryInterface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;

public class TeamDbRepository extends AbstractDbRepository<UUID, Team> implements TeamRepositoryInterface
{
    public TeamDbRepository(Properties properties)
    {
        super(properties, "team");
    }

    @Override
    protected Team resultToEntity(ResultSet rs) throws SQLException
    {
        return new Team(UUID.fromString(rs.getString("uuid")), rs.getString("name"));
    }

    @Override
    public Team get(UUID uuid)
    {
        return super.get(uuid, "uuid");
    }

    @Override
    public void add(Team entity)
    {
        logger.traceEntry();
        logger.info("Adding team to database");
        String sql = "INSERT INTO team VALUES(?, ?)";
        try
        {
            var connection = jdbcUtils.getConnection();
            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getId().toString());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.executeUpdate();
        } catch (Exception e)
        {
            logger.error(e);
        }
    }

    @Override
    public void update(Team entity)
    {
        logger.traceEntry();
        logger.info("Updating team with id:" + entity.getId());
        String sql = "UPDATE team SET name = ? WHERE uuid = ?";
        try
        {
            var connection = jdbcUtils.getConnection();
            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getId().toString());
            preparedStatement.executeUpdate();
        } catch (Exception e)
        {
            logger.error(e);
        }
    }

    @Override
    public Iterable<Team> getTeamsByPartialName(String partialName)
    {
        var result = new ArrayList<Team>();
        var sql = "SELECT * FROM team WHERE name LIKE ?";
        try
        {
            var connection = jdbcUtils.getConnection();
            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + partialName + "%");
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                result.add(resultToEntity(resultSet));
            }
            return result;
        } catch (Exception e)
        {
            logger.error(e);
            return null;
        }
    }
}
