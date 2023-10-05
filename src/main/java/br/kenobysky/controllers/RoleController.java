package br.kenobysky.controllers;

import br.kenobysky.models.Role;
import br.kenobysky.services.RoleService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "/api/roles")
public class RoleController {

    @Autowired
    private RoleService service;


    @PostMapping
    @Transactional
    public ResponseEntity<Role> save(@Valid @RequestBody Role role, UriComponentsBuilder uriBuilder) {
     
        //Salva o role
        role = service.save(role);

        URI uri = uriBuilder.path("/api/roles/{id}").buildAndExpand(role.getId()).toUri();

        return ResponseEntity.created(uri).body(role);
    }

    @PutMapping
    public ResponseEntity<Role> update(@RequestBody @Valid Role role) {
        //Salva o role
        role = service.save(role);

        //Devolve o 
        return ResponseEntity.ok(role);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<Role> getOne(@PathVariable("id") Long id) {
        Optional<Role> role = service.get(id);

        if (role.isPresent()) {

            return ResponseEntity.ok((role.get()));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Role> delete(@PathVariable("id") Long id, Model model) {
        Optional<Role> role = service.get(id);

        if (role.isPresent()) {
            service.delete(role.get());
        } else {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }


}
