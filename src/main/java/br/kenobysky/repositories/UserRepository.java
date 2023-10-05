package br.kenobysky.repositories;

import br.kenobysky.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH  u.roles WHERE u.id = ?1")
    Optional<User> get(Long id);

}
