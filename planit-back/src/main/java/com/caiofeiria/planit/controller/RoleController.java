package com.caiofeiria.planit.controller;

import com.caiofeiria.planit.models.Role;
import com.caiofeiria.planit.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "http://localhost:4200")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        Optional<Role> role = roleService.findById(id);
        return role.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nome/{nome}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> getRoleByNome(@PathVariable String nome) {
        Optional<Role> role = roleService.findByNome(nome);
        return role.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        if (roleService.existsByNome(role.getNome())) {
            return ResponseEntity.badRequest().build();
        }
        Role savedRole = roleService.save(role);
        return ResponseEntity.ok(savedRole);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role role) {
        if (!roleService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        role.setId(id);
        Role updatedRole = roleService.save(role);
        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        if (!roleService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        roleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
