package internal.andreiva.domain;

import java.util.Objects;
import java.util.UUID;

public class User extends Entity
{
    private final String username;
    private final String passwordHash;

    public User(UUID id, String username, String passwordHash)
    {
        super(id);
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPasswordHash()
    {
        return passwordHash;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(passwordHash, user.passwordHash);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(username, passwordHash);
    }
}
