package internal.andreiva.concursmotociclism.service;

import internal.andreiva.concursmotociclism.repository.UserRepositoryInterface;

public class UserController
{
    private final UserRepositoryInterface userRepository;

    public UserController(UserRepositoryInterface userRepository)
    {
        this.userRepository = userRepository;
    }


    public boolean checkPassword(String username, String passwordHash)
    {
        var hash = userRepository.getUserByUsername(username).getPasswordHash();
        return hash.equals(passwordHash);
    }
}
