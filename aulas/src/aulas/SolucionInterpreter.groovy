package aulas

import static mip.Solver.ε

class SolucionInterpreter {
	
//	Solucion parse(String filename) {
	Solucion interpret(Map<String, Object> sol) {
		// NOTA: se podría filtrar la solución con tres filtros, uno para cada
		// familia de variables (X, Y, Z) y que cada filtrada sea recibida x un
		// parser distinto. Plan B, tener un Dispatcher que elija el parser
		// correcto a cada línea.
		Map resueltas = [:]
		Set noResueltas = []
//		new File(filename).eachLine {
		// TODO: buscar una mejor opción que esta del map<k,v>, ya que para el caso pureBinary
		//		los values siempre se descartan y no tienen mucho sentido.
		sol.each { String k, v ->
			// NOTE: ahora que se pide interpretar un Map, los values pueden ser  
			//		el String asociado a un keyword o el Double asociado a una 
			//		variable. Antes sólo se recibían comentarios y los  
			//		identificadores de variables 'encendidas'. Mejorar tanto lío.
//			String[] tokens = it.split("_")
//			if (tokens[0].startsWith('#')) { // Comentario
//			println "k: $k, v: $v"
			if (k == '#gap')			// FIXME: horrible! debería ir al reporte de la solución!
				println 'gap: ' + v
			if (k[0] == '#') // Comentario o Keyword
				return // do nothing
				
			assert -ε < v && v < 1+ε 
			assert v < ε || 1-ε < v
			if (v < ε)		// parche temporal, mientras se reciben tanto los valores en cero
				return		// como los unos que venían directamente del caso pureBinary.
				
//			if (tokens[0] == 'X') { // Variable de asignacion
			if (k[0] == 'X') { // Variable de asignacion
				// NOTE: eg, k == X_240_1005
				String[] tokens = k.split("_")
				assert tokens.length == 3
				def idClase = parseIdClase(tokens[1])
				def idAula = parseIdAula(tokens[2])
				resueltas[idClase] = idAula
				
//			} else if (tokens[0] == 'Z') { // Variable de clase sin resolver
			} else if (k[0] == 'Z') { // Variable de clase sin resolver
				String[] tokens = k.split("_")
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
