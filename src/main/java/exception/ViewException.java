package exception;

public class ViewException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public static String msgCompetenciaIncorreta = "Competencia inválida, estava esperando um mes/ano (01/2020).";

	public ViewException(String msg) {
		super(msg);
	}
}
