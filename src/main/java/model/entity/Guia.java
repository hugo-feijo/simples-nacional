package model.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Guia {

	private Double valor;
	private LocalDate dataVencimento;
	private LocalDate mesReferencia;
	public Guia(Double valor, LocalDate dataVencimento, LocalDate mesReferencia) {
		super();
		this.valor = valor;
		this.dataVencimento = dataVencimento;
		this.mesReferencia = mesReferencia;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public LocalDate getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public LocalDate getMesReferencia() {
		return mesReferencia;
	}
	public void setMesReferencia(LocalDate mesReferencia) {
		this.mesReferencia = mesReferencia;
	}
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter formatterMes = DateTimeFormatter.ofPattern("L/yyyy");
		
		return String.format("Guia - Valor = R$ %.2f, vencimento = %s, referente = %s", valor, dataVencimento.format(formatter),
				mesReferencia.format(formatterMes));
	}
	
	
}
