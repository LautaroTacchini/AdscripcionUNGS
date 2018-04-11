package aulas

class PreAsignacion implements Comparable<PreAsignacion> {

	// FIXME: en realidad, cambiar x 'Aula a, Clase c'
	
	def asignacion, aula
//	def clase, aula
//	int clase, aula

	int compareTo(PreAsignacion that) {
//		return this.clase - that.clase ?: this.aula - that.aula  
//		return this.clase - that.clase ?: this.aula.compareTo(that.aula)  
		return this.asignacion - that.asignacion ?: this.aula.compareTo(that.aula)  
//		return this.asignacion - that.asignacion ?: this.aula <=> that.aula // probar esto  
	}
	
	String toString() {
		return "[clase/asignacion: $asignacion, aula: $aula]"
	}
	
}
