package aulas

class Puntuador {	
	
	Map puntajes = [
			alumnoSinAula: 100
		]
	
	Problema pb
	
	// FIXME: el problema debería asignarse en el constructor, pero para eso hay que hacer 
	//		varios cambios en los {aulas/exactas}.EscritorLP...
	Puntuador() {
		// Leer los datos de los parametros de configuración.
	}
	
//	int asignarAula(Clase clase, Aula aula) {
	int puntaje(Clase clase, Aula aula) {
		return 0;
	}
	
//	int noAsignar(Clase clase) {
	int puntajePorNoAsignar(Clase clase) {
		
		// FIXME: los toString() no ganan un concurso de belleza
		if (pb.preasignadas.find { it.asignacion.toString() == clase.id.toString() })
			return 0
		return - puntajes["alumnoSinAula"]*clase.cantidadInscriptos;	
	}
}
