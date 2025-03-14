package internal.andreiva.concursmotociclism.repository.db;

import internal.andreiva.concursmotociclism.domain.Team;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.UUID;

public class TeamDbRepository extends AbstractDbRepository<UUID, Team>
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
    public void add(Team entity)
    {
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
}
