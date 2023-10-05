package br.kenobysky.repositories;

import br.kenobysky.models.Client;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c LEFT JOIN FETCH  c.roles WHERE c.id = ?1")
    Optional<Client> get(Long id);

    @Query("SELECT c FROM Client c WHERE c.code = ?1")
    Optional<Client> findByCode(String code);

}
