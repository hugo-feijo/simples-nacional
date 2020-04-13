package model.entity.anexo;

import java.io.FileNotFoundException;

import util.ReadCsvFile;

public class Anexo_II extends Anexo {

	private ReadCsvFile readCsv = new ReadCsvFile();
	
	public  Anexo_II() throws FileNotFoundException {
		super();
		readCsv.setAnexo(readCsv.pathAnexoII, this);
		
	}
	


	

	
}
