package internal.andreiva.concursmotociclism.repository.db;

import internal.andreiva.concursmotociclism.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.UUID;

public class UserDbRepository extends AbstractDbRepository<UUID, User>
{
    public UserDbRepository(Properties properties)
    {
        super(properties, "user");
    }

    @Override
    protected User resultToEntity(ResultSet rs) throws SQLException
    {
        return new User(UUID.fromString(rs.getString("uuid")), rs.getString("username"), rs.getString("password_hash"));
    }

    @Override
    public void add(User entity)
    {
        String sql = "INSERT INTO user (uuid, username, password_hash) VALUES (?, ?, ?)";
        try
        {
            var connection = jdbcUtils.getConnection();
            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getId().toString());
            preparedStatement.setString(2, entity.getUsername());
            preparedStatement.setString(3, entity.getPasswordHash());
            preparedStatement.executeUpdate();
        } catch (Exception e)
        {
            logger.error(e);
        }
    }

    @Override
    public void update(User entity)
    {
        String sql = "UPDATE user SET username=?, password_hash=? WHERE uuid=?";
        try
        {
            var connection = jdbcUtils.getConnection();
            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getPasswordHash());
            preparedStatement.setString(3, entity.getId().toString());
            preparedStatement.executeUpdate();
        } catch (Exception e)
        {
            logger.error(e);
        }
    }
}
