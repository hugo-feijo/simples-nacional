package model.entity.anexo;

import java.io.FileNotFoundException;

import util.ReadCsvFile;

public class Anexo_V extends Anexo {

	private ReadCsvFile readCsv = new ReadCsvFile();

	public  Anexo_V() throws FileNotFoundException {
		super();
		readCsv.setAnexo(readCsv.pathAnexoV, this);
		
	}

}
