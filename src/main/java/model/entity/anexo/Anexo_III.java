package model.entity.anexo;

import java.io.FileNotFoundException;

import util.ReadCsvFile;

public class Anexo_III extends Anexo {

	private ReadCsvFile readCsv = new ReadCsvFile();
	
	public  Anexo_III() throws FileNotFoundException {
		super();
		readCsv.setAnexo(readCsv.pathAnexoIII, this);
		
	}
	


	

	
}
