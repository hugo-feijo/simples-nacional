package model.service;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import exception.BusinessLogicException;
import exception.FormatDataException;
import model.entity.Nota;
import model.entity.enums.FaixasFaturamento;
import util.ReadCsvFile;

public class SimplesNacionalService {//TODO: talvez refatorar esta classe separando uma entidade

	private Double receitaBruta12Meses = 0.0;
	private Double faturamentoMensal = 0.0;
	private List<Nota> notasGeradas = new ArrayList<>();;
	private ReadCsvFile readCsv;
	private LocalDate mesReferente;
	private FaixasFaturamento faixa;
	
	public SimplesNacionalService(ReadCsvFile readCsv) throws FileNotFoundException {//TODO: remover mes referente do construtor
		this.readCsv = readCsv;
		lerNotas();
	}
	
	private void lerNotas() throws FileNotFoundException {
		Stream<Nota> sortedNota = readCsv.lerNotas().stream().sorted((a, b) -> a.getDataEmissao().compareTo(b.getDataEmissao()));
		this.notasGeradas = sortedNota.collect(Collectors.toList());
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
		this.mesReferente = mesReferente;// TODO: lançar exception se mês de referencia for nulo;
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

	public FaixasFaturamento getFaixa() throws FileNotFoundException {
		return encontrarFaixa();
	}

	private FaixasFaturamento encontrarFaixa() throws FileNotFoundException {
		Double receitaBruta = calcularReceitaBruta();
		System.out.println(receitaBruta);
		if(FaixasFaturamento.FAIXA_1_MIN.valor <= receitaBruta &&
				receitaBruta <= FaixasFaturamento.FAIXA_1_MAX.valor) {
			faixa = FaixasFaturamento.FAIXA_1;
		}
		if(FaixasFaturamento.FAIXA_2_MIN.valor <= receitaBruta &&
				receitaBruta <= FaixasFaturamento.FAIXA_2_MAX.valor) {
			faixa = FaixasFaturamento.FAIXA_2;
		}
		if(FaixasFaturamento.FAIXA_3_MIN.valor <= receitaBruta &&
				receitaBruta <= FaixasFaturamento.FAIXA_3_MAX.valor) {
			faixa = FaixasFaturamento.FAIXA_3;
		}
		if(FaixasFaturamento.FAIXA_4_MIN.valor <= receitaBruta &&
				receitaBruta <= FaixasFaturamento.FAIXA_4_MAX.valor) {
			faixa = FaixasFaturamento.FAIXA_4;
		}
		if(FaixasFaturamento.FAIXA_5_MIN.valor <= receitaBruta &&
				receitaBruta <= FaixasFaturamento.FAIXA_5_MAX.valor) {
			faixa = FaixasFaturamento.FAIXA_5;
		}
		if(FaixasFaturamento.FAIXA_6_MIN.valor <= receitaBruta &&
				receitaBruta <= FaixasFaturamento.FAIXA_6_MAX.valor) {
			faixa = FaixasFaturamento.FAIXA_6;
		}
		
		return faixa;
	}

	public Double calcularReceitaBruta() throws FileNotFoundException {
		if(mesReferente == null) {
			throw new BusinessLogicException(BusinessLogicException.msgMesReferenteNaoInformado);
		}
		LocalDate antesDe = mesReferente.minusDays(mesReferente.getDayOfMonth());
		LocalDate depoisDe = mesReferente.minusMonths(12).minusDays(mesReferente.getDayOfMonth() - 1 );
		
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
