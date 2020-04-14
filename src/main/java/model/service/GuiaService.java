package model.service;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Map;

import model.entity.Guia;
import model.entity.anexo.Anexo;
import model.entity.enums.Siglas;

public class GuiaService {

	private SimplesNacionalService simplesNacionalService;

	public GuiaService(SimplesNacionalService simplesNacionalService) {
		this.simplesNacionalService = simplesNacionalService;
	}

	public Guia calcularGuia(LocalDate mesReferente, Anexo anexo) throws FileNotFoundException {
		simplesNacionalService.setMesReferente(mesReferente);

		Double faturamentoMensal = simplesNacionalService.getFaturamentoMensal();
		Double receitaBruta = simplesNacionalService.getReceitaBruta12Meses();
		Map<Siglas, Double> faixaAnexo = anexo.getFaixaFaturamento(simplesNacionalService.getFaixa());

		Double aliquotaImpost = (receitaBruta * (faixaAnexo.get(Siglas.ALIQ) / 100) - faixaAnexo.get(Siglas.VD)) / 
									receitaBruta;
		
		Double valor = (aliquotaImpost * faturamentoMensal);
		LocalDate dataVencimento = LocalDate.of(mesReferente.getYear(), mesReferente.plusMonths(1).getMonth(), 20);
		Guia guia = new Guia(valor, dataVencimento, mesReferente);
		
		return guia;
	}
	
	

}
