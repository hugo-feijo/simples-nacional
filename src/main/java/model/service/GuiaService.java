package model.service;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import exception.BusinessLogicException;
import exception.ViewException;
import model.entity.Guia;
import model.entity.anexo.Anexo;
import model.entity.anexo.Anexo_I;
import model.entity.anexo.Anexo_II;
import model.entity.anexo.Anexo_III;
import model.entity.anexo.Anexo_IV;
import model.entity.anexo.Anexo_V;
import model.entity.enums.Siglas;
import util.ReadCsvFile;

public class GuiaService {

	private SimplesNacionalService simplesNacionalService;
	private ReadCsvFile readCsv; 
	
	public GuiaService(SimplesNacionalService simplesNacionalService) {
		this.simplesNacionalService = simplesNacionalService;
		this.readCsv = simplesNacionalService.getReadCsv();
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

	public Anexo findAnexo(String txtAnexo) throws FileNotFoundException {
		Anexo anexo = new Anexo(this.readCsv);

		switch (txtAnexo) {
		case "1":
			anexo = new Anexo_I(this.readCsv);
			break;
		case "2":
			anexo = new Anexo_II(this.readCsv);
			break;
		case "3":
			anexo = new Anexo_III(this.readCsv);
			break;
		case "4":
			anexo = new Anexo_IV(this.readCsv);
			break;
		case "5":
			anexo = new Anexo_V(this.readCsv);
			break;
		default:
			throw new ViewException(ViewException.msgAnexoInvalido);
		}
		
		return anexo;
	}

	public ReadCsvFile getReadCsv() {
		return readCsv;
	}

	public void setReadCsv(ReadCsvFile readCsv) {
		this.readCsv = readCsv;
	}

	
}
