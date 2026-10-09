package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import models.Perfil;
import models.Professor;
import models.Status;
import play.data.validation.Valid;
import play.db.jpa.Blob;
import play.libs.MimeTypes;
import play.mvc.Controller;
import play.mvc.With;
import security.Administrador;
import security.Seguranca;

@With(Seguranca.class)
public class Professores extends Controller {

	public static void listar(String termo) {
		List<Professor> professores;
		if (termo == null) {
			professores = Professor.find("status != ?1", Status.INATIVO).fetch();
		} else {
			professores = Professor.find("status != ?1 and (lower(nome) like ?2 or lower(email) like ?2)",
					Status.INATIVO, "%" + termo.toLowerCase() + "%").fetch();
		}
		render(professores, termo);
	}

	public static void verFoto(Long id) {
		Professor professor = Professor.findById(id);
		if (professor.foto == null || !professor.foto.exists()) {
			notFound();
		}
		response.setContentTypeIfNotSet(professor.foto.type());
		renderBinary(professor.foto.get());
	}

	// ===== FOTO: ADMIN altera a de qualquer um; professor altera só a própria =====
	// (sem @Administrador: a permissão é checada no método abaixo)
	public static void formFoto(Long id) {
		Professor p = Professor.findById(id);
		verificarPermissaoFoto(p);
		render(p);
	}

	public static void salvarFoto(Long id, File foto) throws FileNotFoundException {
		Professor professor = Professor.findById(id);
		verificarPermissaoFoto(professor);

		if (foto == null) {
			flash.error("Selecione uma imagem.");
			formFoto(id);
		}

		professor.foto = new Blob();
		professor.foto.set(new FileInputStream(foto), MimeTypes.getContentType(foto.getName()));
		professor.save();

		flash.success("Foto atualizada com sucesso!");
		listar(null);
	}

	// Libera se for ADMIN ou se o professor logado for o dono do cadastro
	private static void verificarPermissaoFoto(Professor professor) {
		boolean admin = Perfil.ADMIN.name().equals(session.get("perfilUsuario"));
		boolean proprio = professor.login != null && professor.login.equals(session.get("usuarioLogado"));
		if (!admin && !proprio) {
			forbidden("Você só pode alterar a sua própria foto");
		}
	}
	// ===============================================================================

	@Administrador
	public static void form() {
		Professor p = new Professor();
		render(p);
	}

	@Administrador
	public static void salvar(@Valid Professor professor) {
		// login precisa ser único, senão dois usuários entrariam com o mesmo login!!!!
		if (professor.login != null && Professor.count("login = ?1", professor.login) > 0) {
			validation.addError("professor.login", "Este login já está em uso.");
		}
		if (professor.email != null && Professor.count("lower(email) = ?1 and status != ?2",
				professor.email.trim().toLowerCase(), Status.INATIVO) > 0) {
			validation.addError("professor.email", "Este e-mail já está cadastrado.");
		}

		if (professor.matricula != null
				&& Professor.count("matricula = ?1 and status != ?2", professor.matricula.trim(), Status.INATIVO) > 0) {
			validation.addError("professor.matricula", "Esta matrícula já está cadastrada.");
		}

		if (validation.hasErrors()) {
			Professor p = professor;
			renderTemplate("Professores/form.html", p);
		}

		professor.email = professor.email.toLowerCase();
		professor.save();
		flash.success("Professor cadastrado com sucesso!");
		listar(null);
	}

	@Administrador
	public static void remover(Long id) {
		Professor professor = Professor.findById(id);
		professor.status = Status.INATIVO;
		professor.save();

		flash.success("Professor removido com sucesso!");
		listar(null);
	}
}