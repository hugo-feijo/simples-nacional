package model.entity.anexo;

import java.io.FileNotFoundException;

import util.ReadCsvFile;

public class Anexo_II extends Anexo {

	
	public  Anexo_II(ReadCsvFile readCsv) throws FileNotFoundException {
		super(readCsv);
		getReadCsv().setAnexo(getReadCsv().pathAnexoII, this);
		
	}
	


	

	
}
