package models;

//Os perfis (papéis) que um usuário pode ter no sistema.
//ADMIN       -> pode tudo (inclusive remover agendamentos)
//PROFESSOR   -> pode ver, criar e editar, mas NÃO remover
public enum Perfil {
	ADMIN, PROFESSOR

}
