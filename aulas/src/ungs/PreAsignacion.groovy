package ungs

class PreAsignacion implements Comparable<PreAsignacion> {

	// FIXME: cambiar x IdClase, IdAula
	// FIXME: en realidad, cambiar x 'Aula a, Clase c' 
	int asignacion, aula

	int compareTo(PreAsignacion that) {
		return this.asignacion - that.asignacion ?: this.aula - that.aula  
	}
	
//	static Set<PreAsignacion> parse(def target) {
//		return new PreAsignacionParser(target).parse()
//	}

}
