package aulas

abstract class Clase implements Comparable<Clase> {
	
	def id
	Date horaDesde, horaHasta
	// Nota: para Calendar.DAY_OF_WEEK, SUNDAY == 1.
	DiaSemana diaSemana
	int cantidadInscriptos

	Clase(def id, Date horaDesde, Date horaHasta, DiaSemana d, int cantInscriptos) {
		this.id = id
		assert horaDesde < horaHasta
		this.horaDesde = horaDesde
		this.horaHasta = horaHasta
		diaSemana = d
		assert 0 <= cantInscriptos
		cantidadInscriptos = cantInscriptos
	}
	
	// NOTE: sería bueno que los días y horarios válidos se definieran en
	// 		una configuración externa. Además, esta validación debiera ser un
	//		postproceso.

	String toString() {
		properties.findAll { !(it.key in ["class", "metaClass"])  }
	}
	
	Intervalo minutoInicial() {
		Date minutoSiguiente = horaDesde.clone()
		minutoSiguiente[Calendar.MINUTE] += 1
		return new Intervalo(diaSemana, horaDesde, minutoSiguiente)
	}
	
	Intervalo minutoFinal() {
		Date minutoAnterior = horaHasta.clone()
		minutoAnterior[Calendar.MINUTE] -= 1
		return new Intervalo(diaSemana, minutoAnterior, horaHasta)
	}

	int puntaje(Aula a) { return 0 }
	
	boolean puedeUsar(Aula aula) {
		return cantidadInscriptos <= aula.capacidad 
	}

	boolean seSolapaCon(Clase that) {
		return seSolapaCon(that.diaSemana, that.horaDesde, that.horaHasta)
	}
	
	boolean seSolapaCon(Intervalo iv) {
		return seSolapaCon(iv.dia, iv.horaInicio, iv.horaFin)
	}
	
	boolean seSolapaCon(DiaSemana d, Date horaInicio, Date horaFin) {
		// Si no caen el mismo día de la semana ya está
		if (diaSemana != d)
			return false
		
		// Si no se solapan las horas desde-hasta, listo
		if (this.horaHasta <= horaInicio || horaFin <= this.horaDesde)
			return false

		// Finalmente, se solapan :p
		return true
	}
	
	int compareTo(Clase that) { this.id.compareTo(that.id) }
	
}
