package util;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import exception.FormatDataException;
import model.entity.Anexo;
import model.entity.Anexo_I;
import model.entity.Anexo_IV;
import model.entity.Nota;
import model.entity.enums.Siglas;

@ExtendWith(MockitoExtension.class)
@DisplayName("Read CSV file deveria")
class ReadCsvFileTest {

	@Mock
	private BufferedReader bufferedReader;
	
	private ReadCsvFile inTest;
	
	@BeforeEach
	void setUp() {
		inTest = new ReadCsvFile();
	}
	
	@Nested
	@DisplayName("Lançar uma exceção")
	class throwException {

		private List<Nota> notas = new ArrayList<>();
		
		@Test
		@DisplayName("se arquivo não existe")
		void deveriaJogarExceçãoSeArquivoNaoExiste() {
			inTest.pathNotas = Paths.get("/arquivo-inextente.csv");
			
			assertThrows(FileNotFoundException.class, () -> inTest.lerNotas());
			assertThrows(FileNotFoundException.class, () -> inTest.setAnexo(inTest.pathNotas, new Anexo()));
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
	
	@Test
	@DisplayName("Seta corretamento o anexo")
	void test_dadosCsvParaAnexoCorretamente() throws FileNotFoundException {
		Anexo anexoI = new Anexo_I();
		assertEquals(4.0, anexoI.faixa_1.get(Siglas.ALIQ));
		assertEquals(7.3, anexoI.faixa_2.get(Siglas.ALIQ));
		assertEquals(19.0, anexoI.faixa_6.get(Siglas.ALIQ));
		
		assertEquals(5940.00, anexoI.faixa_2.get(Siglas.VD));
		assertEquals(13860.00, anexoI.faixa_3.get(Siglas.VD));
		assertEquals(378000.00, anexoI.faixa_6.get(Siglas.VD));

		Anexo anexoIV = new Anexo_IV();
		assertEquals(4.50, anexoIV.faixa_1.get(Siglas.ALIQ));
		assertEquals(9.00, anexoIV.faixa_2.get(Siglas.ALIQ));
		assertEquals(33.00, anexoIV.faixa_6.get(Siglas.ALIQ));
		
		assertEquals(8100.00, anexoIV.faixa_2.get(Siglas.VD));
		assertEquals(12420.00, anexoIV.faixa_3.get(Siglas.VD));
		assertEquals(828000.00, anexoIV.faixa_6.get(Siglas.VD));
	}
	
	

}
