package model.service;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exception.BusinessLogicException;
import exception.FormatDataException;
import model.entity.Nota;
import util.ReadCsvFile;

public class SimplesNacionalService {

	private Double receitaBruta12Meses = 0.0;
	private Double faturamentoMensal = 0.0;
	private List<Nota> notasGeradas = new ArrayList<>();;
	private ReadCsvFile readCsv;
	private LocalDate mesReferente;
	
	public SimplesNacionalService(LocalDate mesReferente, ReadCsvFile readCsv) throws FileNotFoundException {
		this.mesReferente = mesReferente;
		this.readCsv = readCsv;
	}
	
	public List<Nota> getNotasGeradas() {
		return this.notasGeradas;
	}

	public void setNotasGeradas(List<Nota> notasGeradas) {
		this.notasGeradas = notasGeradas;
	}

	public Double getReceitaBruta12Meses() throws FileNotFoundException {
		return calcularReceitaBruta();
	}

	public void setReceitaBruta12Meses(Double receitaBruta12Meses) {
		this.receitaBruta12Meses = receitaBruta12Meses;
	}

	public LocalDate getMesReferente() {
		return mesReferente;
	}

	public void setMesReferente(LocalDate mesReferente) {
		this.mesReferente = mesReferente;
	}

	public Double getFaturamentoMensal() throws FileNotFoundException {
		return calcularFaturamentoMensal();
	}

	public void setFaturamentoMensal(Double faturamentoMensal) {
		this.faturamentoMensal = faturamentoMensal;
	}

	public ReadCsvFile getReadCsv() {
		return readCsv;
	}

	public Double calcularReceitaBruta() throws FileNotFoundException {
		LocalDate antesDe = mesReferente.minusDays(mesReferente.getDayOfMonth());
		LocalDate depoisDe = mesReferente.minusMonths(12).minusDays(mesReferente.getDayOfMonth() - 1 );
		notasGeradas = readCsv.lerNotas();
		
		notasGeradas.forEach(notas -> {
			if(notas.getDataEmissao().isBefore(antesDe) && notas.getDataEmissao().isAfter(depoisDe)) {
				receitaBruta12Meses += notas.getValor();
			}
		});
		
		if (receitaBruta12Meses == 0.0) {
			throw new FormatDataException(FormatDataException.msgArquivoVazio);
		}
		return receitaBruta12Meses;
		
	}
	
	public Double calcularFaturamentoMensal() throws FileNotFoundException {
		notasGeradas = readCsv.lerNotas();
		
		notasGeradas.forEach(notas -> {
			if(notas.getDataEmissao().getMonth() == mesReferente.getMonth() && 
					notas.getDataEmissao().getYear() == mesReferente.getYear()) {
				faturamentoMensal += notas.getValor();
			}
		});
		
		if(faturamentoMensal == 0.0) {
			throw new BusinessLogicException(BusinessLogicException.msgFaturamentoZerado);
		}
		return faturamentoMensal;
	}
	
}
