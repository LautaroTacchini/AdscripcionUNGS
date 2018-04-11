package exactas

import aulas.*

class ClaseExactas extends Clase {
	
	String asignatura, concepto, turno, docentes, depto
	int pabellonPreferido, minimoDePizarrones
	double toleranciaCapacidad
	
	ClaseExactas(String asignatura, String concepto, String turno, DiaSemana d,
			Date horaDesde, Date horaHasta, String docentes, String depto, 
			int cantidadInscriptos, int pabellonPreferido, int minimoDePizarrones, 
			double toleranciaCapacidad) {
			
		super(null, horaDesde, horaHasta, d, cantidadInscriptos)
		this.asignatura = asignatura
		this.concepto = concepto
		this.turno = turno
		this.docentes = docentes
		this.depto = depto
		this.pabellonPreferido = pabellonPreferido
		this.minimoDePizarrones = minimoDePizarrones
		this.toleranciaCapacidad = toleranciaCapacidad
		
		assert(pabellonPreferido in [0,1,2])
		assert(0 <= toleranciaCapacidad && toleranciaCapacidad <= 1)
	}
	
	int puntaje(AulaExactas a) {
		// Asignar una clase a su pabellón preferido no agrega penalidad,
		// mientras que cambiarla de pabellón se penaliza.
		int puntos = (pabellonPreferido == 0?  0 : (a.codigoPabellon == pabellonPreferido ? 0 : -1))		
		puntos -= (minimoDePizarrones <= a.pizarrones? 0 : minimoDePizarrones - a.pizarrones)
		puntos -= (cantidadInscriptos <= a.capacidad? 0 : cantidadInscriptos - a.capacidad)  
		 
		
		// Estábamos repitiendo esto en PuntuadorExactas.
//		int exceso = (a.capacidad / ( (cantidadInscriptos+2) /2));
//		if (exceso >= 3)
//			puntos -= (exceso - 3)
	
		return puntos 
	}
	
	boolean puedeUsar(Aula aula) {
		return cantidadInscriptos <= aula.capacidad*(1 + toleranciaCapacidad)
	}
	
	boolean comparteComisionCon(ClaseExactas c) {
		return asignatura == c.asignatura && turno == c.turno
	}

	boolean minimizarCambiosDeAula() {
		return true
	}

}
