package model.entity.anexo;

import java.util.HashMap;
import java.util.Map;

import model.entity.enums.FaixasFaturamento;
import model.entity.enums.Siglas;
import util.ReadCsvFile;

public class Anexo {

	private ReadCsvFile readCsv;
	
	public Map<Siglas, Double> faixa_1 = new HashMap<>();
	public Map<Siglas, Double> faixa_2 = new HashMap<>();
	public Map<Siglas, Double> faixa_3 = new HashMap<>();
	public Map<Siglas, Double> faixa_4 = new HashMap<>();
	public Map<Siglas, Double> faixa_5 = new HashMap<>();
	public Map<Siglas, Double> faixa_6 = new HashMap<>();
	
	public Anexo(ReadCsvFile readCsv) {
		setFaturamentoMinMax();
		this.readCsv = readCsv;
	}
	
	public void setFaturamentoMinMax() {
		faixa_1.put(Siglas.FAT_MIN, FaixasFaturamento.FAIXA_1_MIN.valor);
		faixa_1.put(Siglas.FAT_MAX, FaixasFaturamento.FAIXA_1_MIN.valor);
		
		faixa_2.put(Siglas.FAT_MIN, FaixasFaturamento.FAIXA_2_MIN.valor);
		faixa_2.put(Siglas.FAT_MAX, FaixasFaturamento.FAIXA_2_MIN.valor);
		
		faixa_3.put(Siglas.FAT_MIN, FaixasFaturamento.FAIXA_3_MIN.valor);
		faixa_3.put(Siglas.FAT_MAX, FaixasFaturamento.FAIXA_3_MIN.valor);
		
		faixa_4.put(Siglas.FAT_MIN, FaixasFaturamento.FAIXA_4_MIN.valor);
		faixa_4.put(Siglas.FAT_MAX, FaixasFaturamento.FAIXA_4_MIN.valor);
		
		faixa_5.put(Siglas.FAT_MIN, FaixasFaturamento.FAIXA_5_MIN.valor);
		faixa_5.put(Siglas.FAT_MAX, FaixasFaturamento.FAIXA_5_MIN.valor);
		
		faixa_6.put(Siglas.FAT_MIN, FaixasFaturamento.FAIXA_6_MIN.valor);
		faixa_6.put(Siglas.FAT_MAX, FaixasFaturamento.FAIXA_6_MIN.valor);
	}

	public Map<Siglas, Double> getFaixaFaturamento(FaixasFaturamento faixa) {
		Map<Siglas, Double> faixaCorreta = new HashMap<>();
		
		if(faixa == FaixasFaturamento.FAIXA_1)
			faixaCorreta = faixa_1;
		if(faixa == FaixasFaturamento.FAIXA_2)
			faixaCorreta = faixa_2;
		if(faixa == FaixasFaturamento.FAIXA_3)
			faixaCorreta = faixa_3;
		if(faixa == FaixasFaturamento.FAIXA_4)
			faixaCorreta = faixa_4;
		if(faixa == FaixasFaturamento.FAIXA_5)
			faixaCorreta = faixa_5;
		if(faixa == FaixasFaturamento.FAIXA_6)
			faixaCorreta = faixa_6;
		
		return faixaCorreta;
	}
	public Map<Siglas, Double> getFaixa_1() {
		return faixa_1;
	}

	public Map<Siglas, Double> getFaixa_2() {
		return faixa_2;
	}

	public Map<Siglas, Double> getFaixa_3() {
		return faixa_3;
	}

	public Map<Siglas, Double> getFaixa_4() {
		return faixa_4;
	}

	public Map<Siglas, Double> getFaixa_5() {
		return faixa_5;
	}

	public Map<Siglas, Double> getFaixa_6() {
		return faixa_6;
	}

	public ReadCsvFile getReadCsv() {
		return readCsv;
	}

	public void setReadCsv(ReadCsvFile readCsv) {
		this.readCsv = readCsv;
	}
	
}