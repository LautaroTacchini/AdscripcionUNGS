package aulas

@Deprecated
class SolucionParser {
	
	Solucion parse(String filename) {
		// NOTA: se podría filtrar el archivo con tres filtros, uno para cada
		// familia de variables (X, Y, Z) y que cada filtrada sea recibida x un
		// parser distinto. Plan B, tener un Dispatcher que elija el parser
		// correcto a cada línea.
		Map resueltas = [:]
		Set noResueltas = []
		new File(filename).eachLine {
			String[] tokens = it.split("_")
			
			if (tokens[0].startsWith('#')) { // Comentario
				
				// do nothing
				
			} else if (tokens[0] == 'X') { // Variable de asignacion
				
				assert tokens.length == 3
				def idClase = parseIdClase(tokens[1])
				def idAula = parseIdAula(tokens[2])
				resueltas[idClase] = idAula
				
			} else if (tokens[0] == 'Z') { // Variable de clase sin resolver

				assert tokens.length == 2
				def idClase = parseIdClase(tokens[1])
				noResueltas << idClase
				
//			} else { assert false }
			} // si llega una 'Y', por ahora la ignora. TODO: pensar qué onda.
			
		}
		return new Solucion(resueltas, noResueltas)
	}
	
	def parseIdClase(String id) { return id }
	def parseIdAula(String id) { return id }

}
