package br.kenobysky.services;

import br.kenobysky.config.MyPasswordEncoder;
import br.kenobysky.models.User;
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
public class UserService implements ServiceDAO<User, Long> {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;


    private final PasswordEncoder passwordEncoder;

    public UserService() {
        passwordEncoder = new MyPasswordEncoder();

    }

    public Page<User> listAll(PageRequest pageable) {
        Page<User> lista = userRepo.findAll(pageable);
        return lista;
    }

    public List<User> listAll() {
        List<User> lista = userRepo.findAll();
        return lista;
    }

    public Optional<User> get(Long id) {
        return userRepo.get(id);
    }

    public User save(User user) {

        encodePassword(user);

        return userRepo.save(user);
    }

    public User save(User user, boolean encodePassword) {
        if (encodePassword) {
            encodePassword(user);
        }

        return userRepo.save(user);
    }

    public User update(User user, boolean encodePassword) {
        if (encodePassword) {
            encodePassword(user);
        }
        return userRepo.save(user);
    }

    public void delete(User user) {

//        for (Informativo informativo : user.getInformativos()) {
//            informativo.getUsuarios().remove(user);
//            informativoService.save(informativo);
//        }
        userRepo.delete(user);
    }

    private void encodePassword(User user) {

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }


}
