package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Laboratorio extends Model {

	@Required
	@MinSize(5)
	public String nome;

	@Required
	public Integer ramal;

	// ATIVO e INATIVO 
	@Enumerated(EnumType.STRING)
	public Status status;

	@OneToMany(mappedBy = "laboratorio")
	public List<Agendamento> agendamentos;

	public Laboratorio() {
		this.status = Status.ATIVO;
	}

	public static List<Laboratorio> listarAtivos() {
		return Laboratorio.find("status != ?1", Status.INATIVO).fetch();
	}
}
