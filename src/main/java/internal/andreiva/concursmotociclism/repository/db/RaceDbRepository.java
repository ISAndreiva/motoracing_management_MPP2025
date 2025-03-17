package internal.andreiva.concursmotociclism.repository.db;

import internal.andreiva.concursmotociclism.domain.Race;
import internal.andreiva.concursmotociclism.repository.RaceRepositoryInterface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;

public class RaceDbRepository extends AbstractDbRepository<UUID, Race> implements RaceRepositoryInterface
{

    public RaceDbRepository(Properties properties)
    {
        super(properties, "race");
    }

    @Override
    protected Race resultToEntity(ResultSet rs) throws SQLException
    {
        return new Race(UUID.fromString(rs.getString("uuid")), rs.getInt("class"), rs.getString("name"));
    }

    @Override
    public void add(Race entity)
    {
        logger.traceEntry();
        logger.info("Adding new Race to database");
        String sql = "INSERT INTO race (uuid, name, class) VALUES (?, ?, ?)";
        try
        {
            var connection = jdbcUtils.getConnection();
            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getId().toString());
            preparedStatement.setString(2, entity.getRaceName());
            preparedStatement.setInt(3, entity.getRaceClass());
            preparedStatement.executeUpdate();
        } catch (Exception e)
        {
            logger.error(e);
        }
    }

    @Override
    public void update(Race entity)
    {
        logger.traceEntry();
        logger.info("Updating Race with id:" + entity.getId());
        String sql = "UPDATE race SET name = ?, class = ? WHERE uuid = ?";
        try
        {
            var connection = jdbcUtils.getConnection();
            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getRaceName());
            preparedStatement.setInt(2, entity.getRaceClass());
            preparedStatement.setString(3, entity.getId().toString());
            preparedStatement.executeUpdate();
        } catch (Exception e)
        {
            logger.error(e);
        }
    }

    @Override
    public Race get(UUID uuid)
    {
        return super.get(uuid, "uuid");
    }

    @Override
    public Iterable<Race> getRacesByClass(int raceClass)
    {
        logger.traceEntry();
        logger.info("Getting Races by class " + raceClass);
        String sql = "SELECT * from race WHERE class = ?";
        var array = new ArrayList<Race>();
        try
        {
            var connection = jdbcUtils.getConnection();
            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, raceClass);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                array.add(resultToEntity(resultSet));
            }
            return array;
        } catch (Exception e)
        {
            logger.error(e);
            return null;
        }
    }
}
