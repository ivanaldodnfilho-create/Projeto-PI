package controllers;

import models.Professor;
import play.mvc.Controller;

public class Logins extends Controller{
	
	public static void form() {
		render();
	}
	
	// Recebe login e senha do formulário (nomes dos parâmetros = "name" dos inputs)
	public static void logar(String login, String senha) {
		Professor usuario = Professor.obterUsuario(login, senha);
		
		if(usuario == null) {
			flash.error("Usuário ou senha inválido. Tente novamente!");
			form(); //volta pro login (o Play interrompe aqui, não precisa de return
		}
		
		// quando ele confere e "retorna" que o login está OK -> guarda os dados na session 
		session.put("usuarioLogado", usuario.login);
		session.put("perfilUsuario", usuario.perfil.name());// "ADMIN" ou "PROFESSOR"
		
		flash.success("Login realizado com sucesso!");
		Agendamentos.listar(null);
	}
	
	//limpa a session inteira
	public static void sair() {
		session.clear();
		form();
	}

}
