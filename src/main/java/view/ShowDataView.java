package view;

import java.util.List;
import java.util.Scanner;

import model.entity.Guia;
import model.entity.Nota;

public class ShowDataView {

	Scanner sc = new Scanner(System.in);

	@SuppressWarnings("unused")
	public void fecharView() {
		String opcao;
		System.out.println("");
		System.out.println("-------OPÇOES-------");
		System.out.println("Selecione uma das opções: ");
		System.out.println("[1] - Voltar ao menu.");
		System.out.print("Insira um opção: ");

		opcao = sc.nextLine();
		clearConsole();
	}
	
	public void clearConsole() {
		for (int i = 0; i < 50; ++i)
			System.out.println();
	}

	public void exibirNotas(List<Nota> notas) {
		notas.forEach(nota -> {
			System.out.println(nota.toString());
		});

		fecharView();
	}

	public void exibirGuias(List<Guia> guias) {
		System.out.println("");
		guias.forEach(guia -> System.out.println(guia.toString()));

		fecharView();
	}
}
