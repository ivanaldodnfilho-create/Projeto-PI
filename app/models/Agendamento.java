package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import exceptions.AgendamentoConflitanteException;
import play.data.validation.Email;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Agendamento extends Model {

	@Required
	@MinSize(5)
	public String nomeResponsavel;

	@Email
	@Required
	public String email;

	@Required
	public Date dataHoraInicio;

	@Required
	public Date dataHoraFim;

	@Required
	@ManyToOne
	public Laboratorio laboratorio;

	@Required
	@ManyToOne
	public Professor professor;

	@Enumerated(EnumType.STRING)
	public Status status;

	public Agendamento() {
		this.status = Status.ATIVO;
	}

	
	public static boolean existeConflitoDeHorario(Laboratorio laboratorio, Date dataHoraInicio,
			Date dataHoraFim, Long idAtual) {

		if (laboratorio == null || laboratorio.id == null || dataHoraInicio == null || dataHoraFim == null) {
			return false;
		}

		String query = "laboratorio.id = ?1 and status != ?2 and dataHoraInicio < ?3 and dataHoraFim > ?4"
				+ (idAtual != null ? " and id != ?5" : "");

		List<Agendamento> conflitantes;
		if (idAtual != null) {
			conflitantes = Agendamento.find(query, laboratorio.id, Status.INATIVO,
					dataHoraFim, dataHoraInicio, idAtual).fetch();
		} else {
			conflitantes = Agendamento.find(query, laboratorio.id, Status.INATIVO,
					dataHoraFim, dataHoraInicio).fetch();
		}

		return !conflitantes.isEmpty();
	}

	public void validarConflitoDeHorario() {
		if (existeConflitoDeHorario(this.laboratorio, this.dataHoraInicio, this.dataHoraFim, this.id)) {
			throw new AgendamentoConflitanteException(
					"Não será possível agendar o laboratório nesse horário, "
					+ "pois já existe um agendamento para o laboratório "
					+ (this.laboratorio != null ? "'" + this.laboratorio.nome + "' " : "")
					+ "nesse período. Verifique a disponibilidade e tente outro horário.");
		}
	}
}