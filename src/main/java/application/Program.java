package application;

import java.io.FileNotFoundException;

import controller.MainController;

public class Program {

	public static void main(String[] args) throws FileNotFoundException {

		MainController mainController = new MainController();
		
		mainController.start();
	}

}
