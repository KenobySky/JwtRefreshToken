package br.kenobysky.services;

import br.kenobysky.config.MyPasswordEncoder;
import br.kenobysky.models.Client;
import br.kenobysky.repositories.UserRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl() {
        passwordEncoder = new MyPasswordEncoder();

    }

    public Page<Client> listAll(PageRequest pageable) {
        Page<Client> lista = userRepository.findAll(pageable);
        return lista;
    }

    public List<Client> listAll() {
        List<Client> lista = userRepository.findAll();
        return lista;
    }

    @Transactional
    public Optional<Client> get(Long id) {
        return userRepository.get(id);
    }

    public Client save(Client user) {

        encodePassword(user);

        return userRepository.save(user);
    }

    @Transactional
    public Client save(Client user, boolean encodePassword) {
        if (encodePassword) {
            encodePassword(user);
        }

        return userRepository.save(user);
    }

    public Client update(Client user, boolean encodePassword) {
        if (encodePassword) {
            encodePassword(user);
        }
        return userRepository.save(user);
    }

    public void delete(Client user) {

//        for (Informativo informativo : user.getInformativos()) {
//            informativo.getUsuarios().remove(user);
//            informativoService.save(informativo);
//        }
        userRepository.delete(user);
    }

    private void encodePassword(Client user) {

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String code) throws UsernameNotFoundException {

        Client user = userRepository.findByCode(code).orElseThrow(() -> new UsernameNotFoundException("User/Client not found"));
        //
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role ->{
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        
        //
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
    
    @Transactional
    public UserDetails loadUserByCode(String code) throws UsernameNotFoundException {

        Client user = userRepository.findByCode(code).orElseThrow(() -> new UsernameNotFoundException("User/Client not found"));
        //
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role ->{
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        
        //
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

}
