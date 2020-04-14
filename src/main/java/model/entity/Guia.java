package model.entity;

import java.time.LocalDate;

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
	
	
}
