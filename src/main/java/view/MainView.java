package view;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import exception.ViewException;

public class MainView {

	Scanner sc = new Scanner(System.in);
	
	public String exibirMenu() {
		String opcao;
		System.out.println("--------------MENU--------------");
		System.out.println("Selecione uma das opções: ");
		System.out.println("[1] - Calcular guia.");
		System.out.println("[2] - Exibir todas as notas cadastradas.");
		System.out.println("[3] - Exibir todas as guias para todas notas cadastradas.");
		System.out.println("[x] - Sair.");
		System.out.print("Insira um opção: ");
		
		opcao = sc.nextLine();
		System.out.println("");
		System.out.println("");
		return opcao.toLowerCase();
	}
	
	public String getMesReferente() {
		String mesReferente;
		System.out.println("Calcular guia referente a qual mês e ano (ex: 01/2020):");
		mesReferente = sc.nextLine();
		return mesReferente;
	}
	
	public String getAnexo() {
		String anexo;
		List<String> opcoes = Arrays.asList("1", "2", "3", "4", "5");
		System.out.println("Qual anexo sua empresa faz parte?");
		System.out.println("Selecione uma das opções: ");
		System.out.println("[1] - Anexo I - Comercio");
		System.out.println("[2] - Anexo II - Industria");
		System.out.println("[3] - Anexo III - Serviços operacionais");
		System.out.println("[4] - Anexo IV - Prestação de serviços");
		System.out.println("[5] - Anexo V - Serviços de desenvolvimento de software");
		System.out.print("Insira um opção: ");
		anexo = sc.nextLine();
		
		while(!opcoes.contains(anexo)) {
			System.out.println(ViewException.msgOpcaoInvalida);
			anexo = sc.nextLine();
		}
		return anexo;
	}
	
	public ArrayList<String> getPeriodo() {
		ArrayList<String> periodo = new ArrayList<String>();
		String periodoDe;
		String periodoAte;
		
		System.out.println("Informe um periodo (ex: 01/2020)");
		System.out.print("De: ");
		periodoDe = sc.nextLine();
		System.out.print("Até: ");
		periodoAte = sc.nextLine();
		
		periodo.add(periodoDe);
		periodo.add(periodoAte);
		
		return periodo;
	}
	
}
