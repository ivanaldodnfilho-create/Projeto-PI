package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

import play.data.validation.Email;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class Professor extends Model {

	@Required
	@MinSize(5)
	public String nome;

	@Email
	@Required
	public String email;

	@Required
	public String matricula;

	@Required
	public String login;

	@Required
	public String senha;

	// Guarda o NOME do enum no banco ("ADMIN"), e não o número.
	@Enumerated(EnumType.STRING)
	public Perfil perfil;

	// ATIVO / INATIVO 
	@Enumerated(EnumType.STRING)
	public Status status;

	public Blob foto;
	
	@OneToMany(mappedBy = "professor")
	public List<Agendamento> agendamentos;

	public Professor() {
		this.status = Status.ATIVO;
		this.perfil = Perfil.PROFESSOR; // quem é cadastrado pelo formulário é professor comum
	}

	// Busca o usuário pelo login e senha (professor removido não entra).
	public static Professor obterUsuario(String login, String senha) {
		return Professor.find("login = ?1 and senha = ?2 and status != ?3",
				login, senha, Status.INATIVO).first();
	}

	public static List<Professor> listarAtivos() {
		return Professor.find("status != ?1", Status.INATIVO).fetch();
	}
}
