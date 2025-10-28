package com.caiofeiria.planit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.caiofeiria.planit.models.Usuario;
import com.caiofeiria.planit.models.Role;
import com.caiofeiria.planit.repositories.UsuarioRepository;
import com.caiofeiria.planit.repositories.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PlanItApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanItApplication.class, args);
	}

	@Bean
	CommandLineRunner seedDefaultUser(UsuarioRepository usuarioRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			usuarioRepository.findByEmail("admin@planit.dev").ifPresentOrElse(
				u -> {},
				() -> {
					Role adminRole = roleRepository.findByNome("ADMIN")
						.orElseThrow(() -> new RuntimeException("Role ADMIN não encontrada"));
					
					Usuario admin = new Usuario();
					admin.setNome("Administrador");
					admin.setEmail("admin@planit.dev");
					admin.setSenha(passwordEncoder.encode("admin123"));
					admin.setRole(adminRole);
					usuarioRepository.save(admin);
					
					System.out.println("Usuário admin criado: admin@planit.dev / admin123");
				}
			);
		};
	}
}
