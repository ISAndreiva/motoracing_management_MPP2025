package internal.andreiva.concursmotociclism.repository.db;

import internal.andreiva.concursmotociclism.domain.RaceRegistration;
import internal.andreiva.concursmotociclism.repository.RaceRegistrationRepositoryInterface;
import internal.andreiva.concursmotociclism.repository.RaceRepositoryInterface;
import internal.andreiva.concursmotociclism.repository.RacerRepositoryInterface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.UUID;

public class RaceRegistrationDbRepository extends AbstractDbRepository<UUID, RaceRegistration> implements RaceRegistrationRepositoryInterface
{
    private final RacerRepositoryInterface racerRepository;
    private final RaceRepositoryInterface raceRepository;

    public RaceRegistrationDbRepository(Properties properties, RacerRepositoryInterface racerRepository, RaceRepositoryInterface raceRepository)
    {
        super(properties, "raceregistration");
        this.racerRepository = racerRepository;
        this.raceRepository = raceRepository;
    }

    @Override
    public RaceRegistration get(UUID uuid)
    {
        return super.get(uuid, "uuid");
    }

    @Override
    protected RaceRegistration resultToEntity(ResultSet rs) throws SQLException
    {
        var racer = racerRepository.get(UUID.fromString(rs.getString("racer")));
        var race = raceRepository.get(UUID.fromString(rs.getString("race")));
        return new RaceRegistration(UUID.fromString(rs.getString("uuid")), race, racer);
    }

    @Override
    public void add(RaceRegistration entity)
    {
        logger.traceEntry();
        logger.info("Adding new RaceRegistration to database");
        String sql = "INSERT INTO raceregistration(uuid, race, racer) VALUES (?, ?, ?)";
        try
        {
            var connection = jdbcUtils.getConnection();
            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getId().toString());
            preparedStatement.setString(2, entity.getRace().getId().toString());
            preparedStatement.setString(3, entity.getRacer().getId().toString());
            preparedStatement.executeUpdate();
        } catch (Exception e)
        {
            logger.error(e);
        }
    }

    @Override
    public void update(RaceRegistration entity)
    {
        logger.traceEntry();
        logger.info("Updating RaceRegistration with id:" + entity.getId());
        String sql = "UPDATE raceregistration SET race = ?, racer = ? WHERE uuid = ?";
        try
        {
            var connection = jdbcUtils.getConnection();
            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getRace().getId().toString());
            preparedStatement.setString(2, entity.getRacer().getId().toString());
            preparedStatement.setString(3, entity.getId().toString());
            preparedStatement.executeUpdate();
        } catch (Exception e)
        {
            logger.error(e);
        }
    }

    @Override
    public Iterable<RaceRegistration> getRegistrationsByRace(UUID raceId)
    {
        return super.getEntitiesByField("race", raceId);
    }

    @Override
    public Iterable<RaceRegistration> getRegistrationsByRacer(UUID racerId)
    {
        return super.getEntitiesByField("racer", racerId);
    }
}
