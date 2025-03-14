package internal.andreiva.concursmotociclism.repository.db;

import internal.andreiva.concursmotociclism.domain.Racer;
import internal.andreiva.concursmotociclism.repository.RacerRepositoryInterface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.UUID;

public class RacerDbRepository extends AbstractDbRepository<UUID, Racer> implements RacerRepositoryInterface
{
    Properties properties;

    public RacerDbRepository(Properties properties)
    {
        super(properties, "racer");
        this.properties = properties;
    }

    @Override
    protected Racer resultToEntity(ResultSet rs) throws SQLException
    {
        var team = new TeamDbRepository(properties).get(UUID.fromString(rs.getString("team")));
        return new Racer(UUID.fromString(rs.getString("uuid")), rs.getString("name"), team, rs.getString("cnp"));
    }

    @Override
    public void add(Racer entity)
    {
        String sql = "INSERT INTO racer(uuid, name, cnp, team) VALUES (?, ?, ?, ?)";
        try
        {
            var connection = jdbcUtils.getConnection();
            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getId().toString());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getCNP());
            preparedStatement.setString(4, entity.getTeam().getId().toString());
            preparedStatement.executeUpdate();
        } catch (Exception e)
        {
            logger.error(e);
        }

    }

    @Override
    public void update(Racer entity)
    {
        String sql = "UPDATE racer SET name = ?, team = ?, cnp = ? WHERE uuid = ?";
        try
        {
            var connection = jdbcUtils.getConnection();
            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getTeam().getId().toString());
            preparedStatement.setString(3, entity.getCNP());
            preparedStatement.setString(4, entity.getId().toString());
            preparedStatement.executeUpdate();
        } catch (Exception e)
        {
            logger.error(e);
        }
    }

    @Override
    public Iterable<Racer> getRacersByTeam(UUID teamId)
    {
        String sql = "SELECT * FROM racer WHERE team = ?";
        return super.sqlToArray(sql, teamId);
    }
}
