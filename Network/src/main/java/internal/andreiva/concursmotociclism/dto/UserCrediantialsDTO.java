package internal.andreiva.concursmotociclism.dto;

public class UserCrediantialsDTO
{
    private final String username;
    private final String password;

    public UserCrediantialsDTO(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

}
