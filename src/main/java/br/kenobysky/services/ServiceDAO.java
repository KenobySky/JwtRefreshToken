package br.kenobysky.services;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 *
 * @author Shadows
 * @param <T> Model que esta sendo utilizada na Service
 * @param <S> Normalmente é utilizado o UUID
 */
public interface ServiceDAO<T, S> {

    Page<T> listAll(PageRequest pageable);

    List<T> listAll();

    T save(T obj);

    void delete(T obj);

    Optional<T> get(S id);

}
