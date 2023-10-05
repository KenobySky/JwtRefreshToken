package br.kenobysky.services;

import br.kenobysky.models.Role;
import br.kenobysky.repositories.RoleRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements ServiceDAO<Role, Long> {

    @Autowired
    private RoleRepository repository;

    @Override
    public Page<Role> listAll(PageRequest pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<Role> listAll() {
        return repository.findAll();
    }

    @Override
    public Role save(Role obj) {
        return repository.save(obj);
    }

    @Override
    public void delete(Role obj) {
        repository.delete(obj);
    }

    @Override
    public Optional<Role> get(Long id) {
        return repository.get(id);

    }
    
    public Optional<Role> get(String name){
        return repository.findByName(name);
    }

    /**
     * Busca por uma keyword em todas as variaveis.
     *
     * @param pageRequest
     * @param keyword
     * @return Uma lista que tem alguma variavel com valor LIKE/IGUAL a keyword.
     */
    public Page<Role> search(PageRequest pageRequest, String keyword) {
        keyword = "%" + keyword + "%";
        return repository.search(keyword, pageRequest);
    }

}
