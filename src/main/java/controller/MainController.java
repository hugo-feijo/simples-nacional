package controller;

import java.io.FileNotFoundException;
import java.time.LocalDate;

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

public class MainController {

	private MainView mainView;
	private SimplesNacionalService simplesService;
	private GuiaService guiaService;

	public MainController(GuiaService guiaService, SimplesNacionalService simplesNacionalService, MainView mainView) {
		this.guiaService = guiaService;
		this.simplesService = simplesNacionalService;
		this.mainView = mainView;
	}

	public void start() {
		try {
			application();
		} catch (BusinessLogicException | FileNotFoundException e) {
			System.out.println("*********");
			System.out.printf("Erro: %s", e.getMessage());
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
				mainView.exibirNotas(simplesService.getNotasGeradas());
				clearConsole();
				break;

			default:
				break;
			}
		}
	}

	public void printGuia() throws FileNotFoundException {
		String competencia = mainView.getMesReferente();
		LocalDate mesReferente = parseStringToLocalDate(competencia);// TODO: imprimir guias, Criar List<guia>
		System.out.println("");
		
		String txtAnexo = mainView.getAnexo();
		Anexo anexo = parseStringToAnexo(txtAnexo);
		System.out.println("");
		
		Guia guia = guiaService.calcularGuia(mesReferente, anexo);
		System.out.println("");
		System.out.println(guia.toString());
		System.out.println("");
	}

	private Anexo parseStringToAnexo(String anexo) throws FileNotFoundException {
		Anexo anexoFinal = new Anexo();

		switch (anexo) {
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
