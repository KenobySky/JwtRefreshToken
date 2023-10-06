package br.kenobysky.services;

import br.kenobysky.enums.TokenType;
import br.kenobysky.models.AuthenticationRequest;
import br.kenobysky.models.AuthenticationResponse;
import br.kenobysky.models.Client;
import br.kenobysky.models.Token;
import br.kenobysky.repositories.TokenRepository;
import br.kenobysky.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getCode(),
                        request.getPassword()
                )
        );
        var user = repository.findByCode(request.getCode()).orElseThrow();
        var jwtToken = jwtService.generateToken(new HashMap<String, Object>(), user);

        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, refreshToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(Client user, String jwtToken) {
        var token = Token.builder()
                .client(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(Client user) {
        tokenRepository.deleteByUser(user.getId());
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String token;
        final String userCode;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        token = authHeader.substring(7);
        userCode = jwtService.extractCode(token);

        if (userCode != null) {
            Optional<Client> client = this.repository.findByCode(userCode);
            
            if (client.isEmpty()) {
                throw new NoSuchElementException("Token Inválido");
            }

            this.tokenRepository.deleteByUser(client.get().getId());
        }

    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String oldRefreshToken;
        final String userCode;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        oldRefreshToken = authHeader.substring(7);
        userCode = jwtService.extractCode(oldRefreshToken);

        if (userCode != null) {
            Optional<Token> findByToken = tokenRepository.findTokenByClient(oldRefreshToken, userCode);
            if (findByToken.isEmpty()) {
                throw new NoSuchElementException("Refresh Token Inválido");
            }

            var user = this.repository.findByCode(userCode).orElseThrow();

            if (jwtService.isTokenValid(oldRefreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                var refreshToken = jwtService.generateRefreshToken(user);

                revokeAllUserTokens(user);
                saveUserToken(user, refreshToken);

                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
