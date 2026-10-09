package security;

import controllers.Logins;
import models.Perfil;
import play.mvc.Before;
import play.mvc.Controller;

public class Seguranca extends Controller {
	
	// INTERCEPTADOR 1 : AUTENTICAÇÃO ("quem é você?")
		// @Before = o Play roda este método ANTES de cada action dos controllers que usam @With(Seguranca.class).
	@Before(priority =1 )//novo
	static void auth() {
		// Se não existe "usuarioLogado" na session, a pessoa não fez login.
		if (!session.contains("usuarioLogado")) {
			flash.error("Restrito para usuários autenticados!");
			Logins.form();  // redireciona para a tela de login e interrompe a action
			
		}
	}
	
	// INTERCEPTADOR 2 : AUTORIZAÇÃO ("você tem permissão?")
	@Before(priority = 2)//novo
	static void verificarAdministrador() {
		String perfil = session.get("perfilUsuario");
		
		// Procura a etiqueta @Administrador na action que está sendo chamada
		Administrador anotacao = getActionAnnotation(Administrador.class);
		
		if (anotacao != null && !Perfil.ADMIN.name().equals(perfil)) {
			forbidden("Acesso restrito aos administradores do sistema");
		}
		
	}

}
