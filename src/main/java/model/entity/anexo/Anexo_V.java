package model.entity.anexo;

import java.io.FileNotFoundException;

import util.ReadCsvFile;

public class Anexo_V extends Anexo {


	public  Anexo_V(ReadCsvFile readCsv) throws FileNotFoundException {
		super(readCsv);
		getReadCsv().setAnexo(getReadCsv().pathAnexoV, this);
		
	}

}
