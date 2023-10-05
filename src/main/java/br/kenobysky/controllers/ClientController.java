package br.kenobysky.controllers;

import br.kenobysky.models.Client;
import br.kenobysky.services.UserService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "/api/clients")
public class ClientController {

    @Autowired
    private UserService service;

    @PostMapping
    @Transactional
    public ResponseEntity<Client> save(@Valid @RequestBody Client user, UriComponentsBuilder uriBuilder) {
        //Salva o user
        user = service.save(user, true);

        URI uri = uriBuilder.path("/api/users/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(user);
    }

    @PutMapping
    public ResponseEntity<Client> update(@RequestBody @Valid Client user) {

        //Salva o consultor
        user = service.save(user, true);

        //Devolve o 
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<Client> getOne(@PathVariable("id") Long id) {
        Optional<Client> user = service.get(id);

        if (user.isPresent()) {

            return ResponseEntity.ok((user.get()));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

}
