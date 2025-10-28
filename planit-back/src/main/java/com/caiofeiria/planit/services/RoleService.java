package com.caiofeiria.planit.services;

import com.caiofeiria.planit.models.Role;
import com.caiofeiria.planit.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    public Optional<Role> findByNome(String nome) {
        return roleRepository.findByNome(nome);
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

    public boolean existsByNome(String nome) {
        return roleRepository.existsByNome(nome);
    }
}
