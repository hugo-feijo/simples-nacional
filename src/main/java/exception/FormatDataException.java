package exception;

public class FormatDataException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public static String msgDataInvalida = "Formato de data invalido, deveria ser 01/12/2020; Por gentilza verifique o arquivo notas-geradas.csv."; 
	public static String msgValorInvalido = "Formato de valor invalido, deveria ser R$ 0.000,00; Por gentilza verifique o arquivo notas-geradas.csv.";
	public static String msgValorNaoInformado = "Não foi encontrado o valor de uma das notas. Por gentileza verifique o arquivo notas-geradas.csv.";

	public static String msgArquivoVazio = "O arquivo notas-geradas.csv está vazio.";

	
	public FormatDataException(String msg) {
		super(msg);
	}
}
