package util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import exception.FormatDataException;
import model.entity.Nota;
import model.entity.anexo.Anexo;
import model.entity.anexo.Anexo_I;
import model.entity.anexo.Anexo_II;
import model.entity.anexo.Anexo_III;
import model.entity.anexo.Anexo_IV;
import model.entity.anexo.Anexo_V;
import model.entity.enums.Siglas;

@ExtendWith(MockitoExtension.class)
@DisplayName("Read CSV file deveria")
class ReadCsvFileTest {

	@Mock
	private BufferedReader bufferedReader;
	
	@InjectMocks
	private ReadCsvFile inTest;
	
	@BeforeEach
	void setUp() {
	}
	
	@Nested
	@DisplayName("Lançar uma exceção")
	class throwException {

		private List<Nota> notas = new ArrayList<>();
		
		@Test
		@DisplayName("tratar um exceção IOException na leitura das notas")
		void test_tratamentoIOExceptionNaNota() throws IOException {
			when(bufferedReader.readLine()).thenThrow(new IOException());
			
			assertFalse(inTest.parseDataCsvToNota(bufferedReader, null));
		}

		@Test
		@DisplayName("tratar um exceção IOException na leitura dos anexos")
		void test_tratamentoIOExceptionNoAnexo() throws IOException {
			when(bufferedReader.readLine()).thenThrow(new IOException());
			
			assertFalse(inTest.parseDataCsvToAnexo(bufferedReader, null));
		}
		
		@Test
		@DisplayName("se arquivo não existe")
		void deveriaJogarExceçãoSeArquivoNaoExiste() {
			inTest.pathNotas = Paths.get("/arquivo-inextente.csv");
			
			assertThrows(FileNotFoundException.class, () -> inTest.lerNotas());
			assertThrows(FileNotFoundException.class, () -> inTest.setAnexo(inTest.pathNotas, new Anexo(inTest)));
		}
		
		@Test
		@DisplayName("se arquivo nota estiver vazio")
		void test_arquivoNotaVazio() {
			inTest.pathNotas = Paths.get("src/test/resources/notas-vazia.csv");
			
			FormatDataException exception = assertThrows(FormatDataException.class, () -> inTest.lerNotas());
			assertEquals(FormatDataException.msgArquivoNotasVazio, exception.getMessage());
		}

		@Test
		@DisplayName("se arquivo anexo estiver vazio")
		void test_arquivoAnexoVazio() throws FileNotFoundException {
			inTest.pathAnexoI = Paths.get("src/test/resources/anexo-I-vazio.csv");
			FormatDataException exception = assertThrows(FormatDataException.class, () -> new Anexo_I(inTest));
			assertEquals(FormatDataException.msgArquivoAnexoVazio, exception.getMessage());
		}
		
		@Nested
		@DisplayName("se o valor")
		class valorIncorreto{

			String linhaArquivoCsv;
			String msgErro;
			
			@Test
			@DisplayName("estiver faltando")
			void test_lancarExcecaoSeValorEstiverFaltandoFormatado() throws IOException {
				when(bufferedReader.readLine()).thenReturn(";01/02/2020");
				
				msgErro = FormatDataException.msgValorNaoInformado;
				
				RuntimeException exception = assertThrows(FormatDataException.class, () -> inTest.parseDataCsvToNota(bufferedReader, notas));
				assertEquals(msgErro, exception.getMessage());
			}
			
			@Test
			@DisplayName("estiver sem o simbolo R$")
			void test_lancarExcecaoSeValorEstiverFaltandoSimboloR$() throws IOException {
				when(bufferedReader.readLine()).thenReturn("100.00;01/02/2020");
		
				msgErro = FormatDataException.msgValorInvalido;
				
				RuntimeException exception = assertThrows(FormatDataException.class, () -> inTest.parseDataCsvToNota(bufferedReader, notas));
				assertEquals(msgErro, exception.getMessage());				
			}

			@Test
			@DisplayName("estiver mal formatado")
			void test_lancarExcecaoSeValorEstiverMalFormatado() throws IOException {
				when(bufferedReader.readLine()).thenReturn("10a0.00;01/02/2020");
				msgErro = FormatDataException.msgValorInvalido;
				
				RuntimeException exception = assertThrows(FormatDataException.class, () -> inTest.parseDataCsvToNota(bufferedReader, notas));
				assertEquals(msgErro, exception.getMessage());				
			}
		}
		
		@Nested
		@DisplayName("se a data")
		class dataIncorreta {
			String linhaArquivoCsv;
			String msgErro;
			
			@Test
			@DisplayName("estiver mal formatada")
			void test_lancarExcecaoSeDataEstiverMalFormata() throws IOException {
				when(bufferedReader.readLine()).thenReturn("R$ 100,00;2020-01-01");
				msgErro = FormatDataException.msgDataInvalida;
				
				RuntimeException exception = assertThrows(FormatDataException.class, () -> inTest.parseDataCsvToNota(bufferedReader, notas));
				assertEquals(msgErro, exception.getMessage());
			}
		}
		
		
	}
	
	
	@Test
	@DisplayName("Retornar um lista de notas")
	void deveriaRetornarUmaListaDeNotas() throws FileNotFoundException {
		Path pathNotasTest = Paths.get("src/test/resources/notas-geradas.csv");
		inTest.pathNotas = pathNotasTest;
		List<Nota> notas = inTest.lerNotas();
		
		assertNotNull(notas);
		assertTrue(notas.size() == 4);
		assertEquals(Nota.class, notas.get(0).getClass());
		
		assertEquals(notas.get(0).getValor(), 76523.68);
		assertEquals(notas.get(1).getValor(), 8742.30);
		assertEquals(notas.get(2).getValor(), 36000.00);
		assertEquals(notas.get(3).getValor(), 768.22);
	}
	
	@Nested
	@DisplayName("setar corretamente o")
	class setAnexos {
		
		@Test
		@DisplayName("anexo I")
		void test_anexoI() throws FileNotFoundException {
			Anexo anexoI = new Anexo_I(inTest);
			assertEquals(4.00, anexoI.faixa_1.get(Siglas.ALIQ));
			assertEquals(7.30, anexoI.faixa_2.get(Siglas.ALIQ));
			assertEquals(9.50, anexoI.faixa_3.get(Siglas.ALIQ));
			assertEquals(10.70, anexoI.faixa_4.get(Siglas.ALIQ));
			assertEquals(14.30, anexoI.faixa_5.get(Siglas.ALIQ));
			assertEquals(19.0, anexoI.faixa_6.get(Siglas.ALIQ));
			
			assertEquals(0.00, anexoI.faixa_1.get(Siglas.VD));
			assertEquals(5940.00, anexoI.faixa_2.get(Siglas.VD));
			assertEquals(13860.00, anexoI.faixa_3.get(Siglas.VD));
			assertEquals(22500.00, anexoI.faixa_4.get(Siglas.VD));
			assertEquals(87300.00, anexoI.faixa_5.get(Siglas.VD));
			assertEquals(378000.00, anexoI.faixa_6.get(Siglas.VD));
			
		}

		@Test
		@DisplayName("anexo II")
		void test_anexoII() throws FileNotFoundException {
			Anexo anexoII = new Anexo_II(inTest);
			assertEquals(4.50, anexoII.faixa_1.get(Siglas.ALIQ));
			assertEquals(7.80, anexoII.faixa_2.get(Siglas.ALIQ));
			assertEquals(10.00, anexoII.faixa_3.get(Siglas.ALIQ));
			assertEquals(11.20, anexoII.faixa_4.get(Siglas.ALIQ));
			assertEquals(14.70, anexoII.faixa_5.get(Siglas.ALIQ));
			assertEquals(30.00, anexoII.faixa_6.get(Siglas.ALIQ));
			
			assertEquals(0.00, anexoII.faixa_1.get(Siglas.VD));
			assertEquals(5940.00, anexoII.faixa_2.get(Siglas.VD));
			assertEquals(13860.00, anexoII.faixa_3.get(Siglas.VD));
			assertEquals(22500.00, anexoII.faixa_4.get(Siglas.VD));
			assertEquals(85500.00, anexoII.faixa_5.get(Siglas.VD));
			assertEquals(720000.00, anexoII.faixa_6.get(Siglas.VD));
			
		}
		
		@Test
		@DisplayName("anexo III")
		void test_anexoIII() throws FileNotFoundException {
			Anexo anexoIII = new Anexo_III(inTest);
			assertEquals(6.00, anexoIII.faixa_1.get(Siglas.ALIQ));
			assertEquals(11.20, anexoIII.faixa_2.get(Siglas.ALIQ));
			assertEquals(13.50, anexoIII.faixa_3.get(Siglas.ALIQ));
			assertEquals(16.00, anexoIII.faixa_4.get(Siglas.ALIQ));
			assertEquals(21.00, anexoIII.faixa_5.get(Siglas.ALIQ));
			assertEquals(33.00, anexoIII.faixa_6.get(Siglas.ALIQ));
			
			assertEquals(0.00, anexoIII.faixa_1.get(Siglas.VD));
			assertEquals(9360.00, anexoIII.faixa_2.get(Siglas.VD));
			assertEquals(17640.00, anexoIII.faixa_3.get(Siglas.VD));
			assertEquals(35640.00, anexoIII.faixa_4.get(Siglas.VD));
			assertEquals(125640.00, anexoIII.faixa_5.get(Siglas.VD));
			assertEquals(648000.00, anexoIII.faixa_6.get(Siglas.VD));
			
		}

		@Test
		@DisplayName("anexo IV")
		void test_anexoIV() throws FileNotFoundException {
			Anexo anexoIV = new Anexo_IV(inTest);
			assertEquals(4.50, anexoIV.faixa_1.get(Siglas.ALIQ));
			assertEquals(9.00, anexoIV.faixa_2.get(Siglas.ALIQ));
			assertEquals(10.20, anexoIV.faixa_3.get(Siglas.ALIQ));
			assertEquals(14.00, anexoIV.faixa_4.get(Siglas.ALIQ));
			assertEquals(22.00, anexoIV.faixa_5.get(Siglas.ALIQ));
			assertEquals(33.00, anexoIV.faixa_6.get(Siglas.ALIQ));
			
			assertEquals(0.00, anexoIV.faixa_1.get(Siglas.VD));
			assertEquals(8100.00, anexoIV.faixa_2.get(Siglas.VD));
			assertEquals(12420.00, anexoIV.faixa_3.get(Siglas.VD));
			assertEquals(39780.00, anexoIV.faixa_4.get(Siglas.VD));
			assertEquals(183780.00, anexoIV.faixa_5.get(Siglas.VD));
			assertEquals(828000.00, anexoIV.faixa_6.get(Siglas.VD));
		}

		@Test
		@DisplayName("anexo V")
		void test_anexoV() throws FileNotFoundException {
			Anexo anexoV = new Anexo_V(inTest);
			assertEquals(15.50, anexoV.faixa_1.get(Siglas.ALIQ));
			assertEquals(18.00, anexoV.faixa_2.get(Siglas.ALIQ));
			assertEquals(19.50, anexoV.faixa_3.get(Siglas.ALIQ));
			assertEquals(20.50, anexoV.faixa_4.get(Siglas.ALIQ));
			assertEquals(23.00, anexoV.faixa_5.get(Siglas.ALIQ));
			assertEquals(30.50, anexoV.faixa_6.get(Siglas.ALIQ));
			
			assertEquals(0.00, anexoV.faixa_1.get(Siglas.VD));
			assertEquals(4500.00, anexoV.faixa_2.get(Siglas.VD));
			assertEquals(9900.00, anexoV.faixa_3.get(Siglas.VD));
			assertEquals(17100.00, anexoV.faixa_4.get(Siglas.VD));
			assertEquals(62100.00, anexoV.faixa_5.get(Siglas.VD));
			assertEquals(540000.00, anexoV.faixa_6.get(Siglas.VD));
		}
		
	}
	
	
	

}
