package model.entity;

import java.io.FileNotFoundException;

import util.ReadCsvFile;

public class Anexo_IV extends Anexo {

	private ReadCsvFile readCsv = new ReadCsvFile();

	public  Anexo_IV() throws FileNotFoundException {
		super();
		readCsv.setAnexo(readCsv.pathAnexoIV, this);
		
	}

}
