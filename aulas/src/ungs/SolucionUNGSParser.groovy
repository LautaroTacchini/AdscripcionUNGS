package ungs

@Deprecated
class SolucionUNGSParser extends aulas.SolucionParser {
	
	SolucionUNGS parse(String filename) {
		// NOTA: se podría filtrar el archivo con tres filtros, uno para cada
		// familia de variables (X, Y, Z) y que cada filtrada sea recibida x un
		// parser distinto. Plan B, tener un Dispatcher que elija el parser
		// correcto a cada línea.
		Map<Integer, Integer> resueltas = [:] // Asignacion -> Aula
		Set<Integer> noResueltas = []
		Set<Pair> conAulasDistintas = []
		new File(filename).eachLine {
			String[] tokens = it.split("_")
			
			if (tokens[0].startsWith('#')) { // Comentario
				// do nothing
			} else if (tokens[0] == 'X') { // Variable de asignacion
				
				assert tokens.length == 3
				int codigoAsignacion = tokens[1].toInteger()
				int codigoAula = tokens[2].toInteger()
				resueltas[codigoAsignacion] = codigoAula
				
			} else if (tokens[0] == 'Y') { // Variable de distintas aulas misma comision
			
				assert tokens.length == 3
				int codigoAsignacion1 = tokens[1].toInteger()
				int codigoAsignacion2 = tokens[2].toInteger()
//				conAulasDistintas << new MapEntry(codigoAsignacion1, codigoAsignacion2)
				conAulasDistintas << new Pair(codigoAsignacion1, codigoAsignacion2)
			
			} else if (tokens[0] == 'Z') { // Variable de asignacion sin resolver

				assert tokens.length == 2
				int codigoAsignacion = tokens[1].toInteger()
				noResueltas << codigoAsignacion
				
			} else { assert false }
			
		}
		return new SolucionUNGS(resueltas, noResueltas, conAulasDistintas)
	}

}
