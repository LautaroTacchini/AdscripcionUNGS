package exactas

import java.awt.geom.PathIterator;

import aulas.*

class ClaseParser extends Parser<ClaseExactas> {

	static enum Header { ASIGNATURA, CONCEPTO, TURNO, DIA, PAB, PIZARRONES, AULA, 
		HORA_DESDE, HORA_HASTA, DOCENTES, DEPTO, CANTIDAD_INSCRIPTOS, TOLERANCIA_CAPACIDAD
	}
	
	String header = "Asignatura,Concepto,Turno,Día,Pab.,Pizarrones,Aula,Desde,Hasta," +
			"Docentes,Depto,Kant,Tolerancia capacidad" 
			
	ClaseParser() { super() }
	
	void validateHeader(String text) { 
		assert text.startsWith(header) // El xls hoy sigue con ",,,,," (sic.)
	}

	/** Asigna códigos consecutivos a cada Clase.
	 * Al leer los pedidos de aula para cada Clase, el xls original no incluye
	 * un código numérico que facilite la identificación. Con este identificador
	 * global, a cada fila se le asigna su nro de fila, en el archivo original.
	 * A lo mejor hay soluciones más lindas pero esta anda bien y es baratita.
	 */
	static int globalId
		
	@Override
	Set<Aula> read(def filename) {
		globalId = 2 // no muy lindo pero funciona.
		return readExcel(filename, "Pedidos")
	}
	
	ClaseExactas parse(String str) {
		
		Map tokens = slice(Header.class, str)
		Header.with {
			if (tokens.isEmpty()) { // debug
				println str
			}
			if (tokens[DIA]=="") { // debug
				println str
				println tokens
				// se pincha todo si hay una coma en el nombre de una materia
				// o en cualquier otro lado.
			}
			DiaSemana dia = DiaSemana.parse(tokens[DIA])
			
			// see java.text.SimpleDateFormat
			String timeFormat = "H:mm" // xej: 8:00, 12:00
			Date horaDesde = Date.parse(timeFormat, tokens[HORA_DESDE])
			Date horaHasta = Date.parse(timeFormat, tokens[HORA_HASTA])
//			println str
			int inscriptos = tokens[CANTIDAD_INSCRIPTOS].toInteger()
			int pabellonPreferido = 0 // indistinto
			if (tokens[PAB].isInteger())
				pabellonPreferido = tokens[PAB].toInteger()
			int pizarrones = 0
			if (tokens[PIZARRONES].isInteger())
				pizarrones = tokens[PIZARRONES].toInteger()
			double tolerancia = tokens[TOLERANCIA_CAPACIDAD].toDouble()/100
		
			// Cuando tiene cero inscriptos le ponemos 1 de prepo porque 
			// si no al modelo le da lo mismo asignarla o no
			return new ClaseExactas(tokens[ASIGNATURA], tokens[CONCEPTO],
				tokens[TURNO], dia, horaDesde, horaHasta, tokens[DOCENTES],
				tokens[DEPTO], (inscriptos > 0? inscriptos : 1),  
				pabellonPreferido, pizarrones, tolerancia)
//				tokens[DEPTO], inscriptos, pabellonPreferido, pizarrones, tolerancia)
		}
	}
	
	Set<ClaseExactas> addToContext(ClaseExactas c, Set<ClaseExactas> clases) {
		Set<String> diasDeInteres = obtenerDias('diasDeInteres.txt')
		
		if (c.diaSemana.toString() in diasDeInteres) {
			c.id = globalId++ as String 
			return clases << c
	//		return clases 
		}
		return clases
	}
	
	private Set<String> obtenerDias(String nombreArchivo) {
		
		String dias = new File(nombreArchivo).getText()
		Set<String> diasDeInteres = dias.split(",") 
		
		//FIXME agregar assert para chequear si los dias de interes son validos.
		return diasDeInteres
	}
	
	void validateDataLength(String[] tokens, Object[] constants) {
		assert tokens.length >= constants.length
	}
	
}
