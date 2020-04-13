package exception;

public class BusinessLogicException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public static String msgFaturamentoZerado = "O faturamento mensal não pode ser R$ 0,00";

	
	public BusinessLogicException(String msg) {
		super(msg);
	}
}
