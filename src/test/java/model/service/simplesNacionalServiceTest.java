package model.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import exception.BusinessLogicException;
import exception.FormatDataException;
import model.entity.Nota;
import model.entity.enums.FaixasFaturamento;
import util.ReadCsvFile;

@ExtendWith(MockitoExtension.class)
@DisplayName("Simples Nacional Service deveria ")
class simplesNacionalServiceTest {
	
	private LocalDate mesReferente;
	private SimplesNacionalService simplesNacionalService;
	private List<Nota> notas;
	
	@Mock
	private ReadCsvFile readCsv;
	

	@BeforeEach
	void setUp() throws FileNotFoundException {
		mesReferente = LocalDate.now();
		simplesNacionalService = new SimplesNacionalService(readCsv);
		simplesNacionalService.setMesReferente(mesReferente);
		
		notas = new ArrayList<>();
		Nota notaTest = new Nota(null, null);
		notaTest.setValor(1001.00);
		notaTest.setDataEmissao(LocalDate.now().minusDays(2));
		notas.add(notaTest);
		
		notas.add(new Nota(1000.00, LocalDate.now().minusMonths(1)));
		notas.add(new Nota(1000.00, LocalDate.now().minusMonths(2)));
		notas.add(new Nota(1000.00, LocalDate.now().minusMonths(3)));
		notas.add(new Nota(1000.00, LocalDate.now().minusMonths(4)));
		notas.add(new Nota(1000.00, LocalDate.now().minusMonths(11)));
		notas.add(new Nota(1001.00, LocalDate.now().minusMonths(12)));
		
		notas.add(new Nota(1002.00, LocalDate.now().minusMonths(13)));
		notas.add(new Nota(1000.00, LocalDate.now().minusMonths(14)));
		notas.add(new Nota(1000.00, LocalDate.now().minusMonths(14)));
	}
	
	@Nested
	@DisplayName("Lançar um exceção")
	class lancarExcecao {

		@Test
		@DisplayName("se o valor da receita bruta for zerado")
		void test_ExcecaoSeReceitaForZerada() {
			notas = new ArrayList<>();
			simplesNacionalService.setNotasGeradas(notas);
			
			RuntimeException exception = assertThrows(FormatDataException.class, () -> simplesNacionalService.getReceitaBruta12Meses());
			assertEquals(FormatDataException.msgArquivoVazio, exception.getMessage());
		}
		
		@Test
		@DisplayName("se o faturamento mensal for zerado")
		void test_FaturamentoMensalForZerada() {
			simplesNacionalService.setMesReferente(LocalDate.of(2020, 4, 1));
			
			RuntimeException exception = assertThrows(BusinessLogicException.class, () -> simplesNacionalService.getFaturamentoMensal());
			assertEquals(BusinessLogicException.msgFaturamentoZerado, exception.getMessage());
		}
		
	}
	
	@Test
	@DisplayName("Retornar a soma correta da receita bruta dos ultimos 12 meses")
	void test_somarReceitaBruta12Meses() throws FileNotFoundException{
		
		simplesNacionalService.setNotasGeradas(notas);
		
		Double receitaBruta = simplesNacionalService.getReceitaBruta12Meses();
		
		assertEquals(6001, receitaBruta);
	}
	
	@Test
	@DisplayName("Retorna o faturamento mensal correto")
	void test_somaFaturamentoMensal() throws FileNotFoundException {
		when(readCsv.lerNotas()).thenReturn(notas);
		
		simplesNacionalService.setMesReferente(LocalDate.now().minusMonths(14));
		
		assertEquals(2000, simplesNacionalService.getFaturamentoMensal());
	}

	@Test
	@DisplayName("Encontrar a faixa correta")
	void test_faixaImposto() throws FileNotFoundException {
		simplesNacionalService.setNotasGeradas(notas);
		FaixasFaturamento faixa = simplesNacionalService.getFaixa();
		assertEquals(FaixasFaturamento.FAIXA_1, faixa);
	}

}
