package model.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import exception.FormatDataException;
import model.entity.Nota;
import model.entity.enums.FaixasFaturamento;

@DisplayName("Simples Nacional Service deveria ")
class simplesNacionalServiceTest {
	private SimplesNacionalService simplesNacionalService;
	private List<Nota> notas;

	@BeforeEach
	void setUp() throws FileNotFoundException {
		simplesNacionalService = new SimplesNacionalService();
		notas = new ArrayList<>();
	}
	
	@Test
	@DisplayName("Retornar a soma correta da receita bruta dos ultimos 12 meses")
	void test_somarReceitaBruta12Meses() throws FileNotFoundException{
		
		notas.add(new Nota(1001.00, LocalDate.now().minusDays(2)));
		
		notas.add(new Nota(1000.00, LocalDate.now().minusMonths(1)));
		notas.add(new Nota(1000.00, LocalDate.now().minusMonths(2)));
		notas.add(new Nota(1000.00, LocalDate.now().minusMonths(3)));
		notas.add(new Nota(1000.00, LocalDate.now().minusMonths(4)));
		notas.add(new Nota(1000.00, LocalDate.now().minusMonths(11)));
		notas.add(new Nota(1000.00, LocalDate.now().minusMonths(12)));
		
		notas.add(new Nota(1002.00, LocalDate.now().minusMonths(13)));
		notas.add(new Nota(1000.00, LocalDate.now().minusMonths(14)));
		
		simplesNacionalService.setNotasGeradas(notas);
		Double receitaBruta = simplesNacionalService.getReceitaBruta12Meses();
		
		assertEquals(18000, FaixasFaturamento.FAIXA_1_MIN.valor);
		assertEquals(6000, receitaBruta);
	}
	
	@Test
	@DisplayName("Lançar um exceção se o valor da receita bruta for zerado")
	void test_lancarExcecaoSeReceitaForZerada() {
		simplesNacionalService.setNotasGeradas(notas);
		
		RuntimeException exception = assertThrows(FormatDataException.class, () -> simplesNacionalService.getReceitaBruta12Meses());
		assertEquals(FormatDataException.msgArquivoVazio, exception.getMessage());
	}
	
	

}
