package view;

import java.util.List;
import java.util.Scanner;

import model.entity.Guia;
import model.entity.Nota;

public class ShowDataView {

	Scanner sc = new Scanner(System.in);
	public String exibirNotas(List<Nota> notas) {
		String opcao;
		notas.forEach(nota -> {
			System.out.println(nota.toString());
		});
		
		System.out.println("");
		System.out.println("-------OPÇOES-------");
		System.out.println("Selecione uma das opções: ");
		System.out.println("[1] - Voltar ao menu.");
		System.out.print("Insira um opção: ");
		
		opcao = sc.nextLine();
		return opcao;
	}
	
	public String exibirGuias(List<Guia> guias) {
		String opcao;
		guias.forEach(guia -> System.out.println(guia.toString()));

		System.out.println("");
		System.out.println("-------OPÇOES-------");
		System.out.println("Selecione uma das opções: ");
		System.out.println("[1] - Voltar ao menu.");
		System.out.print("Insira um opção: ");
		
		opcao = sc.nextLine();
		return opcao;
	}
}
