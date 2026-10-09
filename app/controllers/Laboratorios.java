package controllers;

import java.util.List;

import models.Laboratorio;
import models.Status;
import play.data.validation.Valid;
import play.mvc.Controller;
import play.mvc.With;
import security.Administrador;
import security.Seguranca;

@With(Seguranca.class)
public class Laboratorios extends Controller {

	public static void listar() {
		List<Laboratorio> laboratorios = Laboratorio.listarAtivos();
		render(laboratorios);
	}

	@Administrador
	public static void form() {
		Laboratorio l = new Laboratorio();
		render(l);
	}

	@Administrador
	public static void salvar(@Valid Laboratorio laboratorio) {
		if (validation.hasErrors()) {
			Laboratorio l = laboratorio;
			renderTemplate("Laboratorios/form.html", l);
		}

		laboratorio.save();
		flash.success("Laboratório cadastrado com sucesso!");
		listar();
	}

	@Administrador
	public static void remover(Long id) {
		Laboratorio laboratorio = Laboratorio.findById(id);
		laboratorio.status = Status.INATIVO;
		laboratorio.save();

		flash.success("Laboratório removido com sucesso!");
		listar();
	}
}
