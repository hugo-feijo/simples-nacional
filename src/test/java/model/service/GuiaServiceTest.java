package model.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import exception.ViewException;
import model.entity.Guia;
import model.entity.anexo.Anexo_I;
import model.entity.anexo.Anexo_II;
import model.entity.anexo.Anexo_III;
import model.entity.anexo.Anexo_IV;
import model.entity.anexo.Anexo_V;
import model.entity.enums.FaixasFaturamento;
import model.entity.enums.Siglas;
import util.ReadCsvFile;

@ExtendWith(MockitoExtension.class)
@DisplayName("Guia service deveria ")
class GuiaServiceTest {

	@Mock
	private SimplesNacionalService simplesNacionalServiceTest;

	@Mock
	private Anexo_I anexo;

	private GuiaService inTest;

	@BeforeEach
	void setUp() {
		inTest = new GuiaService(simplesNacionalServiceTest);
	}

	@Test
	@DisplayName("Calcular o valor do imposto corretamente")
	void test_calcularValorGuia() throws FileNotFoundException {
		LocalDate mesReferente = LocalDate.of(2019, 11, 01);
		Map<Siglas, Double> faixa_2 = new HashMap<>();
		faixa_2.put(Siglas.ALIQ, 7.30);
		faixa_2.put(Siglas.VD, 5940.00);

		when(simplesNacionalServiceTest.getReceitaBruta12Meses()).thenReturn(320000.00);
		when(simplesNacionalServiceTest.getFaturamentoMensal()).thenReturn(50000.00);
		when(simplesNacionalServiceTest.getFaixa()).thenReturn(FaixasFaturamento.FAIXA_2);
		when(anexo.getFaixaFaturamento(FaixasFaturamento.FAIXA_2)).thenReturn(faixa_2);

		Guia guia = inTest.calcularGuia(mesReferente, anexo);

		assertEquals(2721.875, guia.getValor());
		assertEquals(mesReferente, guia.getMesReferencia());
		assertEquals(LocalDate.of(2019, 12, 20), guia.getDataVencimento());
	}

	@Test
	@DisplayName("Retornar a lista com todas as guias")
	void test_listarGuias() throws FileNotFoundException {
		Map<Siglas, Double> faixa_2 = new HashMap<>();
		faixa_2.put(Siglas.ALIQ, 7.30);
		faixa_2.put(Siglas.VD, 5940.00);

		when(simplesNacionalServiceTest.getReceitaBruta12Meses()).thenReturn(320000.00);
		when(simplesNacionalServiceTest.getFaturamentoMensal()).thenReturn(50000.00);
		when(simplesNacionalServiceTest.getFaixa()).thenReturn(FaixasFaturamento.FAIXA_2);
		when(anexo.getFaixaFaturamento(FaixasFaturamento.FAIXA_2)).thenReturn(faixa_2);

		LocalDate de = LocalDate.of(2019, 04, 01);
		LocalDate ate = LocalDate.of(2019, 07, 01);

		assertEquals(4, inTest.calcularTodasGuias(anexo, de, ate).size());
	}

	@Nested
	@DisplayName("Encontrar o anexo correto")
	class EncontrarAnexoCorreto {

		ReadCsvFile readCsv = new ReadCsvFile();

		@BeforeEach
		void setUp() {
			inTest.setReadCsv(readCsv);
		}

		@Test
		@DisplayName("com base no texto")
		void test_retornarAnexoI() throws FileNotFoundException {
			assertAll(() -> assertEquals(Anexo_I.class, inTest.findAnexo("1").getClass()),
					() -> assertEquals(Anexo_II.class, inTest.findAnexo("2").getClass()),
					() -> assertEquals(Anexo_III.class, inTest.findAnexo("3").getClass()),
					() -> assertEquals(Anexo_IV.class, inTest.findAnexo("4").getClass()),
					() -> assertEquals(Anexo_V.class, inTest.findAnexo("5").getClass()));
		 
			assertThrows(ViewException.class, () -> inTest.findAnexo("6"));
		}
	}
}
