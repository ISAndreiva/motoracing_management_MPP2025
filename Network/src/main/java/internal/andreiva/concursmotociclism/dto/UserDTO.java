package internal.andreiva.concursmotociclism.dto;

import internal.andreiva.concursmotociclism.domain.User;

import java.util.UUID;

public class UserDTO extends EntityDTO<UUID>
{
    private final String username;
    private final String passwordHash;

    public UserDTO(UUID id, String username, String passwordHash)
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

    public static UserDTO fromUser(User user)
    {
        return new UserDTO(user.getId(), user.getUsername(), user.getPasswordHash());
    }
    
    public User toUser()
    {
        return new User(getId(), getUsername(), getPasswordHash());
    }
}
