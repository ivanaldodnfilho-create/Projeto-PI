package exceptions;

//exceção lançada quando existe conflito de horário.
public class AgendamentoConflitanteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AgendamentoConflitanteException(String message) {
		super(message);
	}
}
