package blogApp.blogApp.service;



import blogApp.blogApp.dto.AuthRequest;
import blogApp.blogApp.dto.AuthResponse;
import blogApp.blogApp.entity.User;
import blogApp.blogApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse signup(User user) {
        // Check if username already exists
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser.isPresent()) {
            return new AuthResponse("User with this username already exists!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new AuthResponse("Signup successful!");
    }


    public AuthResponse login(AuthRequest request) {
        try {
            User user = userRepository.findByEmail(request.getEmail())
                    .orElse(null);

            if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return new AuthResponse("Invalid credentials");
            }

            return new AuthResponse("Login successful!");
        } catch (Exception e) {
            return new AuthResponse("Invalid credentials"); // Catch unexpected issues
        }
    }

}
