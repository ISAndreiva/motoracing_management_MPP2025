package internal.andreiva.concursmotociclism.repository.db;

import internal.andreiva.concursmotociclism.domain.Entity;
import internal.andreiva.concursmotociclism.repository.RepositoryInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;

public abstract class AbstractDbRepository<Id, E extends Entity<Id>> implements RepositoryInterface<Id, E>
{
    protected final JdbcUtils jdbcUtils;
    protected final Logger logger;
    protected final String tableName;

    public AbstractDbRepository(Properties properties, String tableName)
    {
        this.jdbcUtils = new JdbcUtils(properties);
        logger = LogManager.getLogger();
        this.tableName = tableName;
    }

    protected abstract E resultToEntity(ResultSet rs) throws SQLException;

    protected Iterable<E> getEntitiesByField(String fieldName, Object fieldValue)
    {
        logger.traceEntry();
        logger.info("Getting entities by field: {}  with value: {}", fieldName, fieldValue);
        String sql;
        if (fieldValue == null)
            sql = "SELECT * FROM " + tableName;
        else
            sql = "SELECT * FROM " + tableName + " WHERE " + fieldName + " = ?";
        var array = new ArrayList<E>();
        try
        {
            var connection = jdbcUtils.getConnection();
            var preparedStatement = connection.prepareStatement(sql);
            if (fieldName != null)
                preparedStatement.setObject(1, fieldValue);
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

    @Override
    public abstract void add(E entity);

    @Override
    public abstract void update(E entity);

    @Override
    public abstract E get(Id id);

    protected E get(Id id, String fieldName)
    {
        String sql = "SELECT * FROM " + tableName + " WHERE " + fieldName + " = ?";
        logger.traceEntry();
        logger.info("Getting entity by id: {}", id);
        try
        {
            var connection = jdbcUtils.getConnection();
            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id.toString());
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                return resultToEntity(resultSet);
            }
            return null;
        } catch (Exception e)
        {
            logger.error(e);
            return null;
        }
    }

    @Override
    public Iterable<E> getAll()
    {
        logger.traceEntry();
        logger.info("Getting all entities");
        return getEntitiesByField(null, null);
    }

    @Override
    public void remove(UUID id)
    {
        logger.traceEntry();
        logger.info("Removing entity by id: {}", id);
        String sql = "DELETE FROM " + tableName + " WHERE uuid = ?";
        try
        {
            var connection = jdbcUtils.getConnection();
            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();
        } catch (Exception e)
        {
            logger.error(e);
        }
    }
}
