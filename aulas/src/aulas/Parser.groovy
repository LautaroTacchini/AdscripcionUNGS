package aulas

import java.text.SimpleDateFormat
import jxl.*
import jxl.read.biff.DateRecord;

abstract class Parser<T> {
	
	abstract void validateHeader(String str)
	
	abstract T parse(String str)
	abstract Set<T> addToContext(T elem, Set<T> context)
	
	Boolean hasHeader

	Parser(hasHeader = true) {
		this.hasHeader = hasHeader
	}
	
	Set<T> read(def filename) {
		return readCSV(filename)
	}
	
	Set<T> readCSV(def filename) {
		if (!filename) // caso null 
			return []
		def file = new File(filename)
		assert file.exists() && !file.isDirectory()
		Set<T> result = []
		file.eachLine { lineText, lineNumber ->
			if (hasHeader && lineNumber == 1)
//				assert headerOk(lineText)
				validateHeader(lineText)
			else try {
				// TODO: si el try sale fuera del eachLine este método se parece
				// mucho más a readExcel.
				assert result != null
				result = addToContext(parse(lineText), result)
			} catch (Exception e) {
				def msg = String.format("%s:%d: %s\n",
						file.name, lineNumber, lineText)
				System.err.print(msg)
				throw e
			}
		}
		return result
	}

	Set<T> readExcel(String filename, String sheetname) {
		if (!filename) // caso null
			return []
		def file = new File(filename)
		
		// TODO: este assert debería convertirse en algo más amigable xq cuando no se encuentra
		// 		el archivo explota un poco feo, aunque al menos se entiende qué está pasando.
		assert file.exists() && !file.isDirectory()
		Set<T> result = []
		try {
			return jxlContent(file, sheetname)
		} catch (Exception e) {
			throw e
		}
	}

	String asCSV(Cell[] cells) {
		// Java 1.4 code to handle embedded quotes
		//  System.out.println("\"" + row[j].getContents()
//				.replaceAll("\"","\"\"") + "\""));
		return cells.collect{ format it }.join(',')
	}
	
	// see http://www.andykhan.com/jexcelapi/tutorial.html#dates
	static SimpleDateFormat timeFormat 
	static {
		TimeZone gmtZone = TimeZone.getTimeZone("GMT")
		timeFormat = new SimpleDateFormat("H:mm");
		timeFormat.setTimeZone(gmtZone);
	}

	String format(Cell c) {
		if (c.contents.contains(',')) {
			println 'ERROR de lectura.'
			println 'Esta versión del programa no puede procesar datos que contengan una coma (",").'
			println 'Celda actual: ' + c.contents
			// FIXME: otra cosa que está fea es que cuando acá uno quiere que explote, sería 
			//		bastante ayuda tener más información de contexto: nombre de archivo, nombre
			//		de la hoja, fila y columna del excel o nro de línea si fuera un archivo de 
			//		texto, etc.
			System.exit(-1)
		}
		if (c.type != CellType.DATE)
			return c.contents
		assert c.isTime()			
		return timeFormat.format(c.getDate());
	}
		
	Set<T> jxlContent(File file, String sheetname) {
		Set<T> result = []
		WorkbookSettings ws = new WorkbookSettings();
		ws.setEncoding("CP1252"); // Encoding de Windows
		Sheet s = Workbook.getWorkbook(file, ws).getSheet(sheetname);
		validateHeader(asCSV(s.getRow(0)))
//		println "rows: " + s.getRows()
		// a veces getRow() devuelve más que las filas con contenido. No está claro si incluye
		// celdas con formato o cómo cuenta las filas, pero hay de más y hace falta saltear las
		// filas vacías.
		for (int i = 1 ; i < s.getRows() ; i++)  {
			// NOTE: algunas veces el getRow devuelve valores mayores a lo que uno espera
			// 		mirando el Excel. Quizás tome celdas con formato o algo así, no está claro.
			//		Este if raro está para que no se rompa la lectura de datos.
			if (s.getRow(i).length == 0)	// 'if' raro.
				continue					// 'if' raro.
				
//			println i	// debug
			def lineText = asCSV(s.getRow(i))
			assert result != null
			if ((lineText =~ /,+/).matches()) // una secuencia de comas.
				break // fila vacía incluida por getRows()
			result = addToContext(parse(lineText), result)
		}
		return result
	}
	
	Map slice(Class<? extends Enum<?>> e, String str) {
		def result = new EnumMap(e)
		String[] tokens = str.split(",")
//		assert(tokens.length == e.enumConstants.length)
		validateDataLength(tokens, e.enumConstants)
		for (Enum key : e.enumConstants) {
			result[key] = tokens[key.ordinal()]
		}
		return result
	}
	
	void validateDataLength(String[] tokens, Object[] constants) {
		assert tokens.length == constants.length
	}

}
