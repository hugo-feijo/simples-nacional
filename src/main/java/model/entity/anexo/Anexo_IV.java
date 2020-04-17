package model.entity.anexo;

import java.io.FileNotFoundException;

import util.ReadCsvFile;

public class Anexo_IV extends Anexo {


	public  Anexo_IV(ReadCsvFile readCsv) throws FileNotFoundException {
		super(readCsv);
		getReadCsv().setAnexo(getReadCsv().pathAnexoIV, this);
		
	}

}
