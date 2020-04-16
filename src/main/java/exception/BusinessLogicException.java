package exception;

public class BusinessLogicException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public static String msgPeriodoIncorreto = "O campo 'Até' tem que ser maior que o campo 'De'";

	public static String msgFaturamentoZerado = "O faturamento mensal não pode ser R$ 0,00";

	public static String msgMesReferenteNaoInformado = "O periodo de referencia não foi informado";

	
	public BusinessLogicException(String msg) {
		super(msg);
	}
}
