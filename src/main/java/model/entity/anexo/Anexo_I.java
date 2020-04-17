package model.entity.anexo;

import java.io.FileNotFoundException;

import util.ReadCsvFile;

public class Anexo_I extends Anexo {

	
	public  Anexo_I(ReadCsvFile readCsv) throws FileNotFoundException {
		super(readCsv);
		getReadCsv().setAnexo(getReadCsv().pathAnexoI, this);
		
	}
	


	

	
}
