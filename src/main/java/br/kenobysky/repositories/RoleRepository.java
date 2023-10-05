package br.kenobysky.repositories;

import br.kenobysky.models.Role;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r FROM Role r WHERE r.name LIKE ?1")
    Page<Role> search(String keyword, PageRequest page);

    @Query("SELECT r FROM Role r WHERE r.name = ?1")
    Optional<Role> findByName(String name);

    @Query("SELECT r FROM Role r WHERE r.id = ?1")
    Optional<Role> get(Long id);
}
