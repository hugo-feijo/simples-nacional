package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import exception.FormatDataException;
import model.entity.Nota;
import model.entity.anexo.Anexo;
import model.entity.enums.Siglas;

public class ReadCsvFile {

	String first;
	public Path pathNotas = Paths.get("src/main/resources/notas-geradas.csv");
	public Path pathAnexoI = Paths.get("src/main/resources/anexo-I.csv");
	public Path pathAnexoII = Paths.get("src/main/resources/anexo-II.csv");
	public Path pathAnexoIII = Paths.get("src/main/resources/anexo-III.csv");
	public Path pathAnexoIV = Paths.get("src/main/resources/anexo-IV.csv");
	public Path pathAnexoV = Paths.get("src/main/resources/anexo-V.csv");
	public Path pathAnexoVI = Paths.get("src/main/resources/anexo-VI.csv");

	public List<Nota> lerNotas() throws FileNotFoundException{
		List<Nota> notas = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(pathNotas.toString()));
		try{
			parseDataCsvToNota(br, notas);
			if(notas.isEmpty())
				throw new FormatDataException(FormatDataException.msgArquivoVazio);
		} catch (IOException e) {
			e.printStackTrace();
		} 

		return notas;
	}

	public void setAnexo(Path path, Anexo anexos) throws FileNotFoundException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toString()));
		try {
			parseDataCsvToAnexo(bufferedReader, anexos);
			if(!anexos.faixa_1.containsKey(Siglas.ALIQ)) 
				throw new FormatDataException(FormatDataException.msgArquivoVazio);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void parseDataCsvToAnexo(BufferedReader br, Anexo anexos) throws IOException {
		String line = "";
		while ((line = br.readLine()) != null) {
			String[] linhas = line.split(";");
			Double valorDeducao = parseStringToDouble(linhas[2]);
			
			if(linhas[0].contains("1")) {
				anexos.faixa_1.put(Siglas.ALIQ, Double.parseDouble(linhas[1]));
				anexos.faixa_1.put(Siglas.VD, valorDeducao);
			}

			if(linhas[0].contains("2")) {
				anexos.faixa_2.put(Siglas.ALIQ, Double.parseDouble(linhas[1]));
				anexos.faixa_2.put(Siglas.VD, valorDeducao);
			}

			if(linhas[0].contains("3")) {
				anexos.faixa_3.put(Siglas.ALIQ, Double.parseDouble(linhas[1]));
				anexos.faixa_3.put(Siglas.VD, valorDeducao);
			}

			if(linhas[0].contains("4")) {
				anexos.faixa_4.put(Siglas.ALIQ, Double.parseDouble(linhas[1]));
				anexos.faixa_4.put(Siglas.VD, valorDeducao);
			}

			if(linhas[0].contains("5")) {
				anexos.faixa_5.put(Siglas.ALIQ, Double.parseDouble(linhas[1]));
				anexos.faixa_5.put(Siglas.VD, valorDeducao);
			}
			
			if(linhas[0].contains("6")) {
				anexos.faixa_6.put(Siglas.ALIQ, Double.parseDouble(linhas[1]));
				anexos.faixa_6.put(Siglas.VD, valorDeducao);
			}
			
		}

	}

	public void parseDataCsvToNota(BufferedReader br, List<Nota> notas) throws IOException {
		String line = "";
		while ((line = br.readLine()) != null) {
			String[] notasLidas = line.split(";");


			Double valor;
			LocalDate data;
			try {
				valor = parseStringToDouble(notasLidas[0]);

				data = LocalDate.parse(notasLidas[1], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				notas.add(new Nota(valor, data));

			} catch (NumberFormatException e) {
				throw new FormatDataException(FormatDataException.msgValorInvalido);
			} catch (DateTimeParseException e) {
				throw new FormatDataException(FormatDataException.msgDataInvalida);
			}

		}

	}

	public Double parseStringToDouble(String text) {
		Double valor;
		
		if (text.isBlank() || text == null) {
			throw new FormatDataException(FormatDataException.msgValorNaoInformado);
		} else {
			text = text.replace(".", "");
			text = text.replace(",", ".");
		}
		
		if (text.contains("R$")) {
			text = text.replace("R$", "");
			text = text.replaceAll(" ", "");
			valor = Double.valueOf(text);
		} else {
			throw new NumberFormatException();
		}
		
		return valor;
	}

}
