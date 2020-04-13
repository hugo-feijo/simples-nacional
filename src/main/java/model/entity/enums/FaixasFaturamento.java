package model.entity.enums;

public enum FaixasFaturamento {

	FAIXA_1_MIN(180000.00),
	FAIXA_1_MAX(180000.00),
	
	FAIXA_2_MIN(180000.01),
	FAIXA_2_MAX(360000.00),
	
	FAIXA_3_MIN(360000.01),
	FAIXA_3_MAX(720000.00),
	
	FAIXA_4_MIN(720000.01),
	FAIXA_4_MAX(1800000.00),
	
	FAIXA_5_MIN(1800000.01),
	FAIXA_5_MAX(3600000.00),
	
	FAIXA_6_MIN(3600000.01),
	FAIXA_6_MAX(4800000.00);
	
	
	public final Double valor;
	private FaixasFaturamento(Double valor) {
		this.valor = valor;
	}
}
