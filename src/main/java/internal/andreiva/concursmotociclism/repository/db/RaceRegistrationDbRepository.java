package internal.andreiva.concursmotociclism.repository.db;

import internal.andreiva.concursmotociclism.domain.RaceRegistration;
import internal.andreiva.concursmotociclism.repository.RaceRegistrationRepositoryInterface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.UUID;

public class RaceRegistrationDbRepository extends AbstractDbRepository<UUID, RaceRegistration> implements RaceRegistrationRepositoryInterface
{
    Properties properties;

    public RaceRegistrationDbRepository(Properties properties)
    {
        super(properties, "raceregistration");
        this.properties = properties;
    }

    @Override
    protected RaceRegistration resultToEntity(ResultSet rs) throws SQLException
    {
        var racer = new RacerDbRepository(properties).get(UUID.fromString(rs.getString("racer")));
        var race = new RaceDbRepository(properties).get(UUID.fromString(rs.getString("race")));
        return new RaceRegistration(UUID.fromString(rs.getString("uuid")), race, racer, rs.getInt("class"));
    }

    @Override
    public void add(RaceRegistration entity)
    {
        String sql = "INSERT INTO raceregistration(uuid, race, racer, class) VALUES (?, ?, ?, ?)";
        try
        {
            var connection = jdbcUtils.getConnection();
            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getId().toString());
            preparedStatement.setString(2, entity.getRace().getId().toString());
            preparedStatement.setString(3, entity.getRacer().getId().toString());
            preparedStatement.setInt(4, entity.getRaceClass());
            preparedStatement.executeUpdate();
        } catch (Exception e)
        {
            logger.error(e);
        }
    }

    @Override
    public void update(RaceRegistration entity)
    {
        String sql = "UPDATE raceregistration SET race = ?, racer = ?, class = ? WHERE uuid = ?";
        try
        {
            var connection = jdbcUtils.getConnection();
            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getRace().getId().toString());
            preparedStatement.setString(2, entity.getRacer().getId().toString());
            preparedStatement.setInt(3, entity.getRaceClass());
            preparedStatement.setString(4, entity.getId().toString());
            preparedStatement.executeUpdate();
        } catch (Exception e)
        {
            logger.error(e);
        }
    }

    @Override
    public Iterable<RaceRegistration> getRegistrationsByRace(UUID raceId)
    {
        String sql = "SELECT * FROM raceregistration WHERE race = ?";
        return super.sqlToArray(sql, raceId);
    }

    @Override
    public Iterable<RaceRegistration> getRegistrationsByRacer(UUID racerId)
    {
        String sql = "SELECT * FROM raceregistration WHERE racer = ?";
        return super.sqlToArray(sql, racerId);
    }
}
