package controller;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;

import exception.BusinessLogicException;
import exception.ViewException;
import model.entity.Guia;
import model.entity.anexo.Anexo;
import model.entity.anexo.Anexo_I;
import model.entity.anexo.Anexo_II;
import model.entity.anexo.Anexo_III;
import model.entity.anexo.Anexo_IV;
import model.entity.anexo.Anexo_V;
import model.service.GuiaService;
import model.service.SimplesNacionalService;
import view.MainView;
import view.ShowDataView;

public class MainController {

	private MainView mainView;
	private ShowDataView showData;
	private SimplesNacionalService simplesService;
	private GuiaService guiaService;

	public MainController(MainView mainView, ShowDataView showData, SimplesNacionalService simplesService,
			GuiaService guiaService) {
		this.mainView = mainView;
		this.showData = showData;
		this.simplesService = simplesService;
		this.guiaService = guiaService;
	}

	public void start() {
		try {
			application();
		} catch (BusinessLogicException | FileNotFoundException | ViewException e) {
			System.out.println("*********");
			System.out.printf("Erro: %s\n", e.getMessage());
			System.out.println("*********");
		}
	}

	public void application() throws FileNotFoundException {
		String opcao = "";
		while (opcao != null) {
			opcao = mainView.exibirMenu();
			switch (opcao) {
			case "1":
				printGuia();
				break;

			case "2":
				showData.exibirNotas(simplesService.getNotasGeradas());
				clearConsole();
				break;
			case "3":
				Anexo anexo = getAnexoOfString();
				LocalDate[] periodo = getPeriodo();
				showData.exibirGuias(guiaService.calcularTodasGuias(anexo, periodo[0], periodo[1]));
				break;
			
			case "x":
				System.out.println("Tchau....");
				opcao = null;
			default:
				System.out.println("Opção invalida");
				break;
			}
		}
	}

	private LocalDate[] getPeriodo() {
		ArrayList<String> txtPeriodo = mainView.getPeriodo();
		LocalDate periodoDe = parseStringToLocalDate(txtPeriodo.get(0));
		LocalDate periodoAte = parseStringToLocalDate(txtPeriodo.get(1));
		LocalDate[] periodo = {periodoDe, periodoAte};
		if(periodoDe.isAfter(periodoAte)) {
			throw new BusinessLogicException(BusinessLogicException.msgPeriodoIncorreto);
		} else {
			return periodo;			
		}
	}

	public void printGuia() throws FileNotFoundException {
		String competencia = mainView.getMesReferente();
		LocalDate mesReferente = parseStringToLocalDate(competencia);// TODO: imprimir guias, Criar List<guia>
		System.out.println("");
		
		
		Anexo anexo = getAnexoOfString();
		System.out.println("");
		
		Guia guia = guiaService.calcularGuia(mesReferente, anexo);
		System.out.println("");
		System.out.println(guia.toString());
		System.out.println("");
	}

	private Anexo getAnexoOfString() throws FileNotFoundException {
		String txtAnexo = mainView.getAnexo();
		Anexo anexoFinal = new Anexo();

		switch (txtAnexo) {
		case "1":
			anexoFinal = new Anexo_I();
			break;
		case "2":
			anexoFinal = new Anexo_II();
			break;
		case "3":
			anexoFinal = new Anexo_III();
			break;
		case "4":
			anexoFinal = new Anexo_IV();
			break;
		case "5":
			anexoFinal = new Anexo_V();
			break;
		default:
			throw new ViewException(ViewException.msgAnexoInvalido);
		}

		return anexoFinal;
	}

	private LocalDate parseStringToLocalDate(String competencia) {
		if (competencia.contains("/") && competencia.length() == 7) {
			String[] txtSpit = competencia.split("/");
			LocalDate mesReferente = LocalDate.of(Integer.parseInt(txtSpit[1]), Integer.parseInt(txtSpit[0]), 1);
			return mesReferente;
		} else {
			throw new ViewException(ViewException.msgCompetenciaIncorreta);
		}
	}

	public void clearConsole() {
		for (int i = 0; i < 50; ++i)
			System.out.println();
	}
}
