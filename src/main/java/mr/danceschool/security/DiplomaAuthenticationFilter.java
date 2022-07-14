package mr.danceschool.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import mr.danceschool.entity.User;
import mr.danceschool.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class DiplomaAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserService userService;
    private String jwtSecret;
    private long jwtExpirationDate;
    private final AuthenticationManager authenticationManager;

    public DiplomaAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, String jwtSecret, long jwtExpirationDate){
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtSecret = jwtSecret;
        this.jwtExpirationDate = jwtExpirationDate;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String login = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = userService.findByLogin(((org.springframework.security.core.userdetails.User)authentication.getPrincipal()).getUsername());
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());

        String accessToken = JWT.create()
                .withSubject(user.getLogin())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationDate))
                .withClaim("roles", user.getRoles().stream().map(r -> r.name()).toList())
                .sign(algorithm);
        String refreshToken = JWT.create()
                .withSubject(user.getLogin())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationDate))
                .sign(algorithm);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("id", user.getId()+"");
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);

    }
}
