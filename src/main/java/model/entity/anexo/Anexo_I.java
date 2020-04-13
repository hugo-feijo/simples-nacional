package model.entity.anexo;

import java.io.FileNotFoundException;

import util.ReadCsvFile;

public class Anexo_I extends Anexo {

	private ReadCsvFile readCsv = new ReadCsvFile();
	
	public  Anexo_I() throws FileNotFoundException {
		super();
		readCsv.setAnexo(readCsv.pathAnexoI, this);
		
	}
	


	

	
}
