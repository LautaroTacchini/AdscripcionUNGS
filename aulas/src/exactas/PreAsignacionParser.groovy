package exactas

import aulas.*
import static exactas.ClaseParser.Header

class PreAsignacionParser extends Parser<PreAsignacion> {
	
	//static enum Header { CODIGO_ASIGNACION, CODIGO_AULA }
	
	static int globalId
	
//	PreAsignacionParser(def target) { super(target, false) }
	PreAsignacionParser() { super(false); globalId = 2;}
	
//	boolean headerOk(String text) { true }
	void validateHeader(String text) { }
	
	@Override
	Set<Aula> read(def filename) {
		return readExcel(filename, "Pedidos")
	}

	PreAsignacion parse(String str) {
//		Map tokens = slice(Header.class, str)
		Map<Enum, String> tokens = slice(Header.class, str)
		
		// NOTE: el nombre 'asignacion' aparece en el EscritorLP compartido, x   
		// lo que no alcanza con cambiar en Exactas/PreAsignacion.
		int asignacion = globalId++;
//		int clase = globalId++;
		
		if (tokens[Header.AULA].trim() == "")
			return null;
			
//		def debugRow = globalId - 1
//		if (debugRow > 175) {
//			println 'debugRow: ' + debugRow + ', str: ' + str
//		}
		int codigoPabellon = tokens[Header.PAB].toInteger()
		
		String nombre = tokens[Header.AULA]
		String idAula = "$codigoPabellon" + (nombre.isInteger()?
					nombre.padLeft(3, "0"): nombre)
						
		return new PreAsignacion(asignacion: asignacion, aula: idAula)		
	}
	
	Set<PreAsignacion> addToContext(PreAsignacion a, Set<PreAsignacion> pre) {	
		if (a == null)
			return pre;

		return pre << a
	}
	
	void validateDataLength(String[] tokens, Object[] constants) {
		assert tokens.length >= constants.length
	}
}	