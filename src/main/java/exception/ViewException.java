package exception;

public class ViewException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public static String msgCompetenciaIncorreta = "Competencia inválida, estava esperando um mes/ano (01/2020).";

	public static String msgOpcaoInvalida = "Opção invalida, por gentileze selecione uma opçao";

	public static String msgAnexoInvalido = "Anexo não encontrado";

	public ViewException(String msg) {
		super(msg);
	}
}
