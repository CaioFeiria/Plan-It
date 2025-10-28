package com.caiofeiria.planit.services;

import com.caiofeiria.planit.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class DataInitializationService implements CommandLineRunner {

    @Autowired
    private RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
    }

    private void initializeRoles() {
        // Verificar se as roles já existem
        if (roleService.findByNome("ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setNome("ADMIN");
            adminRole.setDescricao("Administrador do sistema");
            roleService.save(adminRole);
            System.out.println("Role ADMIN criada com sucesso");
        }

        if (roleService.findByNome("ANFITRIAO").isEmpty()) {
            Role anfitriaoRole = new Role();
            anfitriaoRole.setNome("ANFITRIAO");
            anfitriaoRole.setDescricao("Anfitrião de projetos");
            roleService.save(anfitriaoRole);
            System.out.println("Role ANFITRIAO criada com sucesso");
        }

        if (roleService.findByNome("USUARIO").isEmpty()) {
            Role usuarioRole = new Role();
            usuarioRole.setNome("USUARIO");
            usuarioRole.setDescricao("Usuário comum");
            roleService.save(usuarioRole);
            System.out.println("Role USUARIO criada com sucesso");
        }
    }
}
