package br.kenobysky.repositories;

import br.kenobysky.models.Token;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    Optional<Token> findByToken(String token);

    @Query(value = "SELECT t from Token t INNER JOIN Client c on t.client.id = c.id WHERE t.token = ?1 AND t.client.id = ?2")
    Optional<Token> findTokenByClient(String refreshToken, Long clientId);

    @Query(value = "SELECT t from Token t INNER JOIN Client c on t.client.id = c.id WHERE t.token = ?1 AND c.code = ?2")
    Optional<Token> findTokenByClient(String refreshToken, String clientCode);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM token WHERE client_id = ?1", nativeQuery = true)
    int deleteByUser(Long id);

}
