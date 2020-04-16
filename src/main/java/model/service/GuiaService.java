package model.service;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import exception.BusinessLogicException;
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

		Double aliquotaImpost = (receitaBruta * (faixaAnexo.get(Siglas.ALIQ) / 100) - faixaAnexo.get(Siglas.VD))
				/ receitaBruta;

		Double valor = (aliquotaImpost * faturamentoMensal);
		LocalDate dataVencimento = LocalDate.of(mesReferente.getYear(), mesReferente.plusMonths(1).getMonth(), 20);
		Guia guia = new Guia(valor, dataVencimento, mesReferente);

		return guia;
	}

	public List<Guia> calcularTodasGuias(Anexo anexo, LocalDate de, LocalDate ate) throws FileNotFoundException {

		List<Guia> guias = new ArrayList<>();
		LocalDate mesReferente = de.minusMonths(1);

		while (mesReferente.isBefore(ate)) {
			try {
				mesReferente = mesReferente.plusMonths(1);
				Guia guia = calcularGuia(mesReferente, anexo);
				if (guia.getValor() != 0.0 && guia.getValor() != null)
					guias.add(guia);
			} catch (BusinessLogicException e) {
				if(e.getMessage().equals(BusinessLogicException.msgFaturamentoZerado))
					System.out.println("Nem uma nota cadastrada no periodo " + mesReferente.getMonthValue() + "/" + mesReferente.getYear());
			}
		}

		return guias;
	}

}
