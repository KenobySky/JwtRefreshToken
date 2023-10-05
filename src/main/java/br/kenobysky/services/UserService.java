package br.kenobysky.services;

import br.kenobysky.config.MyPasswordEncoder;
import br.kenobysky.models.Client;
import br.kenobysky.repositories.RoleRepository;
import br.kenobysky.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements ServiceDAO<Client, Long> {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;


    private final PasswordEncoder passwordEncoder;

    public UserService() {
        passwordEncoder = new MyPasswordEncoder();

    }

    public Page<Client> listAll(PageRequest pageable) {
        Page<Client> lista = userRepo.findAll(pageable);
        return lista;
    }

    public List<Client> listAll() {
        List<Client> lista = userRepo.findAll();
        return lista;
    }

    public Optional<Client> get(Long id) {
        return userRepo.get(id);
    }

    public Client save(Client user) {

        encodePassword(user);

        return userRepo.save(user);
    }

    public Client save(Client user, boolean encodePassword) {
        if (encodePassword) {
            encodePassword(user);
        }

        return userRepo.save(user);
    }

    public Client update(Client user, boolean encodePassword) {
        if (encodePassword) {
            encodePassword(user);
        }
        return userRepo.save(user);
    }

    public void delete(Client user) {

//        for (Informativo informativo : user.getInformativos()) {
//            informativo.getUsuarios().remove(user);
//            informativoService.save(informativo);
//        }
        userRepo.delete(user);
    }

    private void encodePassword(Client user) {

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }


}
