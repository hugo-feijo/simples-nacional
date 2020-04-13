package model.service;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exception.FormatDataException;
import model.entity.Nota;
import util.ReadCsvFile;

public class SimplesNacionalService {

	private List<Nota> notasGeradas = new ArrayList<>();;
	private Double receitaBruta12Meses = 0.0;
	
	public SimplesNacionalService() throws FileNotFoundException {
		this.notasGeradas = ReadCsvFile.lerArquivo();
	}
	
	public Double calcularReceitaBruta() throws FileNotFoundException {
		LocalDate antesDe = LocalDate.now().minusDays(LocalDate.now().getDayOfMonth() - 1);
		LocalDate depoisDe = LocalDate.now().minusMonths(12).minusDays(LocalDate.now().getDayOfMonth() - 1 );
		
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
	
	public List<Nota> getNotasGeradas() {
		return this.notasGeradas;
	}

	public void setNotasGeradas(List<Nota> notasGeradas) {
		this.notasGeradas = notasGeradas;
	}

	public Double getReceitaBruta12Meses() throws FileNotFoundException {
		return this.receitaBruta12Meses == 0.0 ? calcularReceitaBruta() : this.receitaBruta12Meses;
	}

	public void setReceitaBruta12Meses(Double receitaBruta12Meses) {
		this.receitaBruta12Meses = receitaBruta12Meses;
	}

	
}
