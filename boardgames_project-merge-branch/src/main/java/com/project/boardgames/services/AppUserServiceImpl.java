package com.project.boardgames.services;
import com.project.boardgames.ErrorUtilities.AppException;
import com.project.boardgames.entities.*;
import com.project.boardgames.repositories.AddressRepository;
import com.project.boardgames.repositories.AppUserRepository;
import com.project.boardgames.repositories.CartRepository;
import com.project.boardgames.repositories.JwtTokenRepository;
import com.project.boardgames.utilities.RequestResponse;
import com.project.boardgames.utilities.authentication.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppUserServiceImpl implements AppUserService {
    @PostConstruct
    public void createAdminUser() {
        String adminEmail = "admin@example.com";

        // Check if an ADMIN user already exists
        Optional<AppUser> optionalAdminUser = userRepository.findByEmail(adminEmail);
        if (optionalAdminUser.isPresent()) {
            // An ADMIN user already exists, no need to create a new one
            return;
        }

        String adminPassword = "admin123";
        String encodedPassword = passwordEncoder.encrypt(adminPassword);
        AppUser adminUser = new AppUser();
        adminUser.setFirstName("Admin");
        adminUser.setLastName("User");
        adminUser.setEmail(adminEmail);
        adminUser.setPassword(encodedPassword);
        adminUser.setRole(Role.ADMIN);
        adminUser.setValid(true);
        adminUser.setCreatedAt(LocalDateTime.now());
        userRepository.save(adminUser);
    }
    private final PasswordHandler passwordEncoder;
    private final AppUserRepository userRepository;
    private final CustomAuthenticationProvider authenticationProvider;
    private final JwtTokenRepository jwtTokenRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    CartRepository cartRepository;
    private final JwtTokenService jwtTokenService;

    public AppUserServiceImpl(AppUserRepository userRepository, PasswordHandler passwordEncoder, CustomAuthenticationProvider authenticationProvider, JwtTokenRepository jwtTokenRepository, JwtTokenService jwtTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationProvider = authenticationProvider;
        this.jwtTokenRepository = jwtTokenRepository;
        this.jwtTokenService = jwtTokenService;
    }
    @Override
    public Optional<AppUser> findUserByEmail(String email) {return userRepository.findByEmail(email);}

    @Override
    public RequestResponse<AppUser> getLoggedInUser(HttpServletRequest request) {
        String token = extractBearerToken(request);
        if (token == null) {
            throw new AppException("You are not logged in", HttpStatus.BAD_REQUEST.value(), "fail", true);
        }

        JwtToken jwt = jwtTokenRepository.findByToken(token);
        if (jwt != null) {
            jwtTokenRepository.delete(jwt);
        }
        JwtTokenService jwtS = new JwtTokenService();
        String email = jwtS.extractUsername(token);
        AppUser user = userRepository.findByEmail(email).orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND.value(), "fail", true));
        return new RequestResponse<>(true, user, "Your data:");
    }

    @Override
    public RequestResponse<AppUser> createUser(AppUser user) {
        return null;
    }

    @Override
    public RequestResponse<AppUser> getUserById(Long userId) {
        return null;
    }

    @Override
    public RequestResponse<AppUser> getUserByUsername(String username) {
        return null;
    }

    @Override
    public RequestResponse<List<AppUser>> getAllUsers() {
        List<AppUser> allUsers = userRepository.findAll();
        List<AppUser> filteredUsers = allUsers.stream()
                .filter(user -> !user.getFirstName().equals("DELETED"))
                .collect(Collectors.toList());

        if (filteredUsers.isEmpty()) {
            throw new AppException("There are no results", HttpStatus.NOT_FOUND.value(), "fail", true);
        }

        return new RequestResponse<>(true, filteredUsers, "The registered users: ");
    }
    public RequestResponse<UserPassport> login(HttpServletResponse response, String email, String password) {
        // Authenticate the user

        try {
            authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (AuthenticationException e) {
            throw new AppException("Incorrect credentials.", HttpStatus.BAD_REQUEST.value(), "fail", true);
        }

        // Retrieve the user from the repository
        AppUser user = userRepository.findByEmail(email).orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND.value(), "fail", true));

        // Check if the account is blocked
        if (user.getValid() == false) throw new AppException("THE ACCOUNT IS INVALID!", HttpStatus.FORBIDDEN.value(), "fail", true);
        // Generate the JWT token and create the response
        System.out.println(user.getId());
        JwtToken jwt = jwtTokenRepository.findByUser_Id(user.getId());
        System.out.println(jwt);
        String token = jwtTokenService.generateToken(user);
        UserPassport passport = new UserPassport(token, user.getEmail(), user.getFirstName(), user.getRole().name(), user.getId());


        // Set the Authorization header in the response
        response.setHeader("Authorization", "Bearer " + token);

        return new RequestResponse<>(true, passport, "You were successfully logged in");
    }

    @Override
    public RequestResponse<AppUser> updateUser(AppUser user) {
        // Check if the user exists in the repository
        Optional<AppUser> optionalUser = userRepository.findById(user.getId());
        if (optionalUser.isPresent()) {
            AppUser existingUser = optionalUser.get();

            // Update the user's data
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());

            // Save the updated user
            AppUser updatedUser = userRepository.save(existingUser);

            return new RequestResponse<>(true, updatedUser, "User updated successfully");
        } else {
            throw new AppException("User not found", HttpStatus.NOT_FOUND.value(), "fail", true);
        }
    }

    @Override
    public RequestResponse<String> deleteUser(HttpServletRequest request) {
        // Check if the user exists in the repository
        String token = extractBearerToken(request);
        if (token == null) {
            throw new AppException("You are not logged in", HttpStatus.BAD_REQUEST.value(), "fail", true);
        }
        JwtTokenService jwtS = new JwtTokenService();
        String email = jwtS.extractUsername(token);

        JwtToken jwt = jwtTokenRepository.findByToken(token);
        if (jwt != null) {
            jwtTokenRepository.delete(jwt);
        }

        Optional<AppUser> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            user.setValid(false);
            user.setEmail("DELETED" + user.getId() + "@deleted.d");
            user.setFirstName("DELETED");
            user.setLastName("DELETED");
            user.setAddress(null);
            user.setPassword("DELETED123");
            userRepository.save(user);
            return new RequestResponse<>(true, "", "User deleted successfully");
        } else {
            throw new AppException("User not found", HttpStatus.NOT_FOUND.value(), "fail", true);
        }
    }


    @Override
    public RequestResponse<String> logout(HttpServletRequest request, HttpServletResponse response) {
        String token = extractBearerToken(request);
        if (token == null) {
            throw new AppException("You are not logged in", HttpStatus.BAD_REQUEST.value(), "fail", true);
        }

        JwtToken jwt = jwtTokenRepository.findByToken(token);
        if (jwt != null) {
            jwtTokenRepository.delete(jwt);
        }
        response.setHeader("Authorization", "");
        return new RequestResponse<>(true, "", "Logged out");
    }

    private String extractBearerToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }


    @Override
    public AppUser registerUser(AppUser newUser, Address address) {
        String email = newUser.getEmail();
        newUser.setRole(Role.USER);
        // Check if user already exists with this email address
        if (userRepository.findByEmail(email).isPresent())
            throw new AppException("User under this email already exists.", HttpStatus.BAD_REQUEST.value(), "fail", true);
        LocalDateTime createdAt = LocalDateTime.now();
        newUser.setCreatedAt(createdAt);
        // Check if password and password confirmation match
        if (!newUser.getPassword().equals(newUser.getConfirmPassword())) {
            throw new AppException("Password and password confirmation do not match", HttpStatus.BAD_REQUEST.value(), "fail", true);
        }
        // Check if password meets complexity requirements
        String password = newUser.getPassword();
        if (password.length() < 8 || !password.matches(".*\\d.*")) {
            throw new AppException("Password must be at least 8 characters long and contain at least one number", HttpStatus.BAD_REQUEST.value(), "fail", true);
        }
        newUser.setPassword(passwordEncoder.encrypt(newUser.getPassword()));
        newUser.setConfirmPassword("");
        try {
            AppUser registeredUser = userRepository.save(newUser);
            if(address != null) {
                addressRepository.save(address);
                registeredUser.setAddress(address);
            }
            registeredUser.setValid(true);
            return userRepository.save(registeredUser);
        }catch(Exception e){
            throw new AppException("Something went wrong during the process of registration:" + e.getMessage(), HttpStatus.BAD_REQUEST.value(), "fail", true);
        }
    }


}
