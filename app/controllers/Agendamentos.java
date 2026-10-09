package controllers;

import java.util.List;

import exceptions.AgendamentoConflitanteException;
import models.Agendamento;
import models.Laboratorio;
import models.Professor;
import models.Status;
import play.data.validation.Valid;
import play.mvc.Controller;
import play.mvc.With;
import security.Administrador;
import security.Seguranca;

@With(Seguranca.class) //faz com que  TODAS as actions desta classe exigem login
public class Agendamentos extends Controller {

	@Administrador
	public static void form() {
		Agendamento a = new Agendamento();
		List<Laboratorio> laboratorios = Laboratorio.listarAtivos();
		List<Professor> professores = Professor.listarAtivos();
		render(a, laboratorios, professores);
	}

	@Administrador
	public static void editar(Long id) {
		Agendamento a = Agendamento.findById(id);
		List<Laboratorio> laboratorios = Laboratorio.listarAtivos();
		List<Professor> professores = Professor.listarAtivos();
		renderTemplate("Agendamentos/form.html", a, laboratorios, professores);
	}

	public static void listar(String termo) {
		List<Agendamento> agendamentos = null;
		if (termo == null) {
			agendamentos = Agendamento.find("status != ?1", Status.INATIVO).fetch();
		} else {
			agendamentos = Agendamento.find(
				"status != ?1 and (lower(nomeResponsavel) like ?2 or lower(email) like ?2)",
				Status.INATIVO, "%" + termo.toLowerCase() + "%").fetch();
		}
		render(agendamentos, termo);
	}

	
	public static void detalhar(Long id) {
		Agendamento agendamento = Agendamento.findById(id);
		render(agendamento);
	}

	@Administrador
	public static void salvar(@Valid Agendamento agendamento) {
		if (validation.hasErrors()) {
			Agendamento a = agendamento;
			List<Laboratorio> laboratorios = Laboratorio.listarAtivos();
			List<Professor> professores = Professor.listarAtivos();
			renderTemplate("Agendamentos/form.html", a, laboratorios, professores);
		}

		agendamento.nomeResponsavel = agendamento.nomeResponsavel.toUpperCase();
		agendamento.email = agendamento.email.toLowerCase();

		try {
			agendamento.validarConflitoDeHorario();
		} catch (AgendamentoConflitanteException e) {
			flash.error(e.getMessage());
			Agendamento a = agendamento;
			List<Laboratorio> laboratorios = Laboratorio.listarAtivos();
			List<Professor> professores = Professor.listarAtivos();
			renderTemplate("Agendamentos/form.html", a, laboratorios, professores);
			return;
		}

		agendamento.save();
		flash.success("Agendamento cadastrado com sucesso!");
		listar(null);
	}

	@Administrador //tem que ser ADMIN
	public static void remover(Long id) {
		Agendamento agendamento = Agendamento.findById(id);
		agendamento.status = Status.INATIVO;
		
		boolean edicao = agendamento.id != null; // tem que vir ANTES do save()
		agendamento.save();
		flash.success(edicao ? "Agendamento atualizado com sucesso!" : "Agendamento cadastrado com sucesso!");
		listar(null);
	}
}