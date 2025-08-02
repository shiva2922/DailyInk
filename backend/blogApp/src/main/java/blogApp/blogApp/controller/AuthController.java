package blogApp.blogApp.controller;



import blogApp.blogApp.dto.AuthRequest;
import blogApp.blogApp.dto.AuthResponse;
import blogApp.blogApp.entity.User;
import blogApp.blogApp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public AuthResponse signup(@RequestBody User user) {
        return authService.signup(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }
}
