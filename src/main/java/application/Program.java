package application;

import java.io.FileNotFoundException;

import controller.MainController;
import model.service.GuiaService;
import model.service.SimplesNacionalService;
import util.ReadCsvFile;
import view.MainView;
import view.ShowDataView;

public class Program {

	public static void main(String[] args) throws FileNotFoundException {

		ReadCsvFile readCsv = new ReadCsvFile();
		MainView mainView = new MainView();
		ShowDataView showData = new ShowDataView();
		SimplesNacionalService simplesNacionalService = new SimplesNacionalService(readCsv);
		GuiaService guiaService = new GuiaService(simplesNacionalService);
		
		MainController mainController = new MainController(mainView, showData, simplesNacionalService, guiaService);
		
		mainController.start();
	}

}
