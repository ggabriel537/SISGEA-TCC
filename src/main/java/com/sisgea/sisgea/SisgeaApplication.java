package com.sisgea.sisgea;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.sisgea.BancoDados.Controllers.AdministradorController;
import com.sisgea.Entidades.Administrador;
import com.sisgea.Entidades.Usuario;

@SpringBootApplication
@EnableScheduling
public class SisgeaApplication {

	public static void main(String[] args) {
		gerarAdminInicial();
		SpringApplication.run(SisgeaApplication.class, args);
	}

	private static void gerarAdminInicial(){
		List<Administrador> admins = AdministradorController.listarAdministradores();
		if(!admins.isEmpty()) return;
		Administrador admin = new Administrador();
		Usuario usuario = new Usuario();
		usuario.setPermissao(1);
		usuario.setUsuario("sisgea");
		usuario.setSenha("sisgea123");
		admin.setNome("Administrador Geral");
		admin.setAtivo(true);
		admin.setUsuario(usuario);
		AdministradorController.salvarAdministrador(admin);
		System.out.println("Nenhum administrador encontrado. Administrador inicial criado!");
	}
}
