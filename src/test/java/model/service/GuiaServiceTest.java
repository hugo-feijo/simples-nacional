package model.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import model.entity.Guia;
import model.entity.anexo.Anexo_I;
import model.entity.enums.FaixasFaturamento;
import model.entity.enums.Siglas;

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

}
