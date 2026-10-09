package jobs;

import models.Laboratorio;
import models.Perfil;
import models.Professor;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class Inicializador extends Job {

	@Override
	public void doJob() throws Exception {

		if (Laboratorio.count() != 0) {
			return;
		}

		Laboratorio l1 = new Laboratorio();
		l1.nome = "Laboratório de Informática 01";
		l1.ramal = 100;
		l1.save();

		Laboratorio l2 = new Laboratorio();
		l2.nome = "Laboratório de Informática 02";
		l2.ramal = 101;
		l2.save();
		
		Laboratorio l3 = new Laboratorio();
		l3.nome = "Laboratório de Informática 03";
		l3.ramal = 102;
		l3.save();
		
		Laboratorio l4 = new Laboratorio();
		l4.nome = "Laboratório de Química";
		l4.ramal = 103;
		l4.save();
		
		Laboratorio l5 = new Laboratorio();
		l5.nome = "Laboratório de Biologia";
		l5.ramal = 104;
		l5.save();
		
		Laboratorio l6 = new Laboratorio();
		l6.nome = "Laboratório de Línguas";
		l6.ramal = 105;
		l6.save();
		
		Laboratorio l7 = new Laboratorio();
		l7.nome = "Laboratório de Física";
		l7.ramal = 106;
		l7.save();
		
		
		Professor p1 = new Professor();
		p1.nome = "Lucas Dantas";
		p1.email = "lucas.dantas@ifrn.edu.br";
		p1.matricula = "1167824";
		p1.login = "Lucas";                 
		p1.senha = "lucas123";                
		p1.perfil = Perfil.PROFESSOR;       //  perfil.PROFESSOR->usuário comum
		p1.save();
		

		Professor p2 = new Professor();
		p2.nome = "Renan Ramalho";
		p2.email = "renan.ramalho@ifrn.edu.br";
		p2.matricula = "2339676";
		p2.login = "Renan";                 
		p2.senha = "renan123";                 
		p2.perfil = Perfil.PROFESSOR;       
		p2.save();
		
		Professor p3 = new Professor();
		p3.nome = "Erick Mateus Souza Oliveira";
		p3.email = "erick.oliveira@ifrn.edu.br";
		p3.matricula = "3488987";
		p3.login = "Erick";                
		p3.senha = "erick123";                 
		p3.perfil = Perfil.PROFESSOR;      
		p3.save();
		
		Professor p4 = new Professor();
		p4.nome = "Genickson Borges de Carvalho";
		p4.email = "genickson.carvalho@ifrn.edu.br";
		p4.matricula = "2066174";
		p4.login = "Genickson";                 
		p4.senha = "genickson123";                 
		p4.perfil = Perfil.PROFESSOR;    
		p4.save();
		
		Professor p5 = new Professor();
		p5.nome = "Cesar Augusto de Freitas Azevedo";
		p5.email = "cesar.augusto@ifrn.edu.br";
		p5.matricula = "1055585";
		p5.login = "Cesar";                
		p5.senha = "cesar123";                 
		p5.perfil = Perfil.PROFESSOR;       
		p5.save();
		
		Professor p6 = new Professor();
		p6.nome = "George Henrique da Silva Viana";
		p6.email = "george.viana@ifrn.edu.br";
		p6.matricula = "3536492";
		p6.login = "George";                 
		p6.senha = "george123";                
		p6.perfil = Perfil.PROFESSOR;       
		p6.save();
		
		Professor p7 = new Professor();
		p7.nome = "Francesco de Araujo Lopes";
		p7.email = "francesco.lopes@ifrn.edu.br";
		p7.matricula = "1829207";
		p7.login = "Francesco";                 
		p7.senha = "francesco123";                
		p7.perfil = Perfil.PROFESSOR;       
		p7.save();

		//ADMINISTRADOR: cadastra/remove prof e lab
		Professor p8 = new Professor();
		p8.nome = "Abrands";
		p8.email = "abrands@ifrn.edu.br";
		p8.matricula = "0000001";
		p8.login = "Abrands";
		p8.senha = "abrands123";
		p8.perfil = Perfil.ADMIN;
		p8.save();
		
		
		
		
		System.out.println("Inicialização executada com sucesso!");
	}
}