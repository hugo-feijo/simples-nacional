package model.entity.enums;

public enum Siglas {
	
	FAT_MIN("Faturamento mínimo"),
	FAT_MAX("Faturamento maximo"),
	ALIQ("Aliquota"),
	VD("Valor a deduzir");
	
	public String descricao;
	private Siglas(String descricao) {
		this.descricao = descricao;
	}

}
