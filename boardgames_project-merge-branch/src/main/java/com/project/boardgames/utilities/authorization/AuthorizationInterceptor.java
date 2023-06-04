package com.project.boardgames.utilities.authorization;

import com.project.boardgames.ErrorUtilities.AppException;
import com.project.boardgames.entities.AppUser;
import com.project.boardgames.repositories.AppUserRepository;
import com.project.boardgames.services.JwtTokenService;
import com.project.boardgames.utilities.authentication.CookieUtil;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
@Order(Ordered.HIGHEST_PRECEDENCE)
@Service
public class AuthorizationInterceptor implements HandlerInterceptor {
    public AuthorizationInterceptor(AppUserRepository userRepository, String role) {
        this.userRepository = userRepository;
        this.role = role;
    }
    AppUserRepository userRepository;
    private String role;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        System.out.println(token);
        if (token == null) {
            throw new AppException("You are not logged in", HttpStatus.UNAUTHORIZED.value(), "fail", true);
        }
        // Remove "Bearer " prefix from the token if present
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        // Check if the user is logged in
        JwtTokenService tokenService = new JwtTokenService();
        Authorization authorization = new Authorization(userRepository);
        AppUser user = authorization.hasPrivileges(token, role);
        request.setAttribute("username", user.getEmail());
        return true;
    }

}

