package application;

import java.io.FileNotFoundException;

import controller.MainController;
import model.service.GuiaService;
import model.service.SimplesNacionalService;
import util.ReadCsvFile;
import view.MainView;

public class Program {

	public static void main(String[] args) throws FileNotFoundException {

		ReadCsvFile readCsv = new ReadCsvFile();
		MainView mainView = new MainView();
		SimplesNacionalService simplesNacionalService = new SimplesNacionalService(readCsv);
		GuiaService guiaService = new GuiaService(simplesNacionalService);
		
		MainController mainController = new MainController(guiaService,simplesNacionalService, mainView);
		
		mainController.start();
	}

}
