package view;
import java.util.List;
import java.util.Scanner;

import model.entity.Nota;

public class MainView {

	Scanner sc = new Scanner(System.in);
	
	public String exibirMenu() {
		String opcao;
		System.out.println("--------------MENU--------------");
		System.out.println("Selecione uma das opções: ");
		System.out.println("[1] - Calcular guia.");
		System.out.println("[2] - Exibir todas as notas cadastrads.");
		System.out.print("Insira um opção: ");
		
		opcao = sc.nextLine();
		System.out.println("");
		System.out.println("");
		return opcao;
	}
	
	public String getMesReferente() {
		String mesReferente;
		System.out.println("Calcular guia referente a qual mês e ano (ex: 01/2020):");
		mesReferente = sc.nextLine();
		return mesReferente;
	}
	
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
}
