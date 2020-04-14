package model.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Nota {

	private Double valor;
	private LocalDate dataEmissao;
	
	public Nota(Double valor, LocalDate dataEmissao) {
		super();
		this.valor = valor;
		this.dataEmissao = dataEmissao;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public LocalDate getDataEmissao() {
		return dataEmissao;
	}
	public void setDataEmissao(LocalDate dataEmissao) {
		this.dataEmissao = dataEmissao;
	}
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return String.format("Emitida em: %s; Valor: R$ %.2f", dataEmissao.format(formatter), valor);
	}
	
}
