package controller;

import java.io.FileNotFoundException;
import java.time.LocalDate;

import exception.FormatDataException;
import exception.ViewException;
import model.service.SimplesNacionalService;
import util.ReadCsvFile;
import view.MainView;

public class MainController {

	private MainView mainView = new MainView();
	private SimplesNacionalService simplesService;
	

	public void start() throws FileNotFoundException {
		simplesService = new SimplesNacionalService(new ReadCsvFile());
		String opcao = "";
		while(opcao != null) {
			opcao = mainView.exibirMenu();
			
			switch (opcao) {
			case "1":
				
				String competencia = mainView.getMesReferente();
				LocalDate mesReferente = parseStringToLocalDate(competencia);//TODO: imprimir guias, Criar List<guia>
				break;
				
			case "2":
				mainView.exibirNotas(simplesService.getNotasGeradas());
				clearConsole();
				break;

			default:
				break;
			}
		}
	}
	
	private LocalDate parseStringToLocalDate(String competencia) {
		if(competencia.contains("/") && competencia.length() == 7) {
			String[] txtSpit =  competencia.split("/");
			LocalDate mesReferente = LocalDate.of(Integer.parseInt(txtSpit[1]), Integer.parseInt(txtSpit[0]), 1);
			return mesReferente;
		} else {
			throw new ViewException(ViewException.msgCompetenciaIncorreta);
		}
	}

	public void clearConsole() {
		for (int i = 0; i < 50; ++i) System.out.println();
	}
}
