package tubespbo.service;

import java.util.Optional;
import tubespbo.domain.User;
import tubespbo.exception.LoginGagalException;
import tubespbo.repository.UserRepository;

interface LoginService {
    User login(String username, String password);
}

// SOLID - Dependency Inversion Principle (DIP):
// Class bergantung pada abstraction (interface LoginService dan UserRepository)
public class AuthService implements LoginService {

    private final UserRepository userRepository;

    // Dependency Injection
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        Optional<User> userOptional = userRepository.cariByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                return user;
            }
        }

        throw new LoginGagalException("Login Gagal! Username atau password salah.");
    }
}