package model.entity.anexo;

import java.io.FileNotFoundException;

import util.ReadCsvFile;

public class Anexo_III extends Anexo {

	
	public  Anexo_III(ReadCsvFile readCsv) throws FileNotFoundException {
		super(readCsv);
		getReadCsv().setAnexo(getReadCsv().pathAnexoIII, this);
		
	}
	


	

	
}
