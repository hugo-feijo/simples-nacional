package controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import model.entity.Guia;
import model.entity.anexo.Anexo;
import model.entity.anexo.Anexo_I;
import model.service.GuiaService;
import model.service.SimplesNacionalService;
import util.ReadCsvFile;
import view.MainView;
import view.ShowDataView;

@ExtendWith(MockitoExtension.class)
@DisplayName("Main Controller deveria")
class MainControllerTest {

	@Mock
	private MainView mainView;
	
	@Mock
	private ShowDataView showData;
	
	@Mock
	private SimplesNacionalService simplesService;
	
	@Mock
	private GuiaService guiaService;
	
	private MainController inTest;
	
	@BeforeEach
	void setUp() {
		inTest = new MainController(mainView, showData, simplesService, guiaService);
	}

	@Test
	@DisplayName("entrar corretamente nas opcões do menu")
	void test_printGuiaUnica() throws FileNotFoundException {
		LocalDate mesReferente = LocalDate.of(2020, 04, 01);
		Guia guia = new Guia(100.00, LocalDate.of(2020, 04, 20), LocalDate.of(2020, 03, 01));
		Anexo anexo = new Anexo_I(new ReadCsvFile());
		
		ArrayList<String> periodo = new ArrayList<>();
		periodo.add("01/2019");
		periodo.add("01/2020");
		
		when(mainView.exibirMenu()).thenReturn("1", "2", "3", "x");
		
		when(mainView.getMesReferente()).thenReturn("04/2020");
		when(mainView.getAnexo()).thenReturn("1");
		when(guiaService.findAnexo("1")).thenReturn(anexo);
		when(guiaService.calcularGuia(eq(mesReferente), any(Anexo.class))).thenReturn(guia);
		
		when(mainView.getPeriodo()).thenReturn(periodo);
		
		InOrder inOrder = inOrder(mainView, showData);
		
		inTest.start();
		inOrder.verify(mainView, times(1)).getMesReferente();
		inOrder.verify(showData, times(1)).exibirNotas(any());
		inOrder.verify(showData, times(1)).exibirGuias(any());
	}
}
