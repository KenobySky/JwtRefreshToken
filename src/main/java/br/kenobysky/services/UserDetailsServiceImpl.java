package br.kenobysky.services;

import br.kenobysky.models.Client;
import br.kenobysky.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String code) throws UsernameNotFoundException {

        Client user = userRepository.findByCode(code).orElseThrow(() -> new UsernameNotFoundException("User/Client not found"));

        return (user);
    }

}
