package ungs

import aulas.*;

class AsignacionUNGS extends aulas.Clase implements Comparable<AsignacionUNGS> {
	
	String periodoLectivo
	boolean cau
	Set<Id> comisiones
	Date fechaDesde, fechaHasta
	// Nota: para Calendar.DAY_OF_WEEK, SUNDAY == 1, lo que coincide con los
	// datos que aparecen en el archivo de entrada.
	Id diaSemanaUNGS, tipoClase

	AsignacionUNGS(String periodoLectivo, boolean esCAU,  
			int codigo, Set<Id> comisiones, Date fechaDesde, Date fechaHasta,
			Date horaDesde, Date horaHasta, Id diaSemana, Id tipoClase, 
			int cantidadInscriptos) {
			
		super(codigo, horaDesde, horaHasta, DiaSemana.parse(diaSemana.nombre), cantidadInscriptos)
		this.periodoLectivo = periodoLectivo
		this.comisiones = comisiones
		this.fechaDesde = fechaDesde
		this.fechaHasta = fechaHasta
		this.diaSemanaUNGS = diaSemana
		this.tipoClase = tipoClase
	}
	
	String toString() {
		String format = "{codigo: %d, fechaDesde: %s, fechaHasta: %s, " +
			"horaDesde: %s, horaHasta: %s, diaSemana: %s, tipoClase: %s, " + 
			"inscriptos: %d, comisiones: %s, periodo: %s, cau: %s"
		return String.format(format, id, 
			fechaDesde.format("dd/MMM/yyyy"), fechaHasta.format("dd/MMM/yyyy"), 
			horaDesde.format("HH:mm"), horaHasta.format("HH:mm"), 
			diaSemanaUNGS.nombre, tipoClase.nombre, cantidadInscriptos, 
			comisiones.toString(), periodoLectivo, cau.toString())
	}
			
	int puntaje(AulaUNGS a) {
		return 0
	}
	
	/**
		Tipo de clase:
		  1 = TEORICO
		  2 = PRACTICO
		  3 = TEORICO/PRACTICO
		  4 = CONSULTA
		  5 = LAB COMPUTACION
		  6 = LAB ECOLOGIA
		  7 = LAB FISICA
		  8 = ESTUDIO DE MEDIOS
		  9 = LAB QUIMICA
		  10 = SALA DE REUNIONES
		  11 = OF DOCENTE
		  12 = SERV. GRALES

		Tipo de aula:
		  1 = AULA COMUN
		  2 = LABO FISICA
		  3 = LABO QUIMICA
		  4 = LABO COMPUTACION
		  5 = LABO COMUNICACION
		  6 = LABO DL ICO (SIG)
		  7 = OFICINA DEL DOCENTE
		  8 = LABO DE ECOLOGIA URBANA
		  9 = SALA DE REUNION

		Asignaciones posibles:
		  {1, 2, 3, 4} --> 1
		  5 --> 4
		  6 --> 8
		  7 --> 2
		  8 --> 5
		  9 --> 3
		  10 --> 1
		  11 --> 7
		  12 --> *
	*/
	boolean puedeUsar(Aula aula) { 
	    if (!aula.activa || aula.capacidad < cantidadInscriptos)
	        return false

		// NOTA: falta implementar la preferencia de aulas y edificios.
		
		return tipoClase.codigo == 12 || aula.tipo.codigo == tipoAulaAsociado()
	}

	int tipoAulaAsociado() {
		// NOTA: ojo que el tipoClase.codigo == 12 se puede asignar a cualquier
		// tipo de aula y por eso se asume que hubo un preproceso que ya separó
		// esas Asignaciones, dado que ya deberían haber sido asignadas
		// manualmente por Servicios Generales. 
		switch (tipoClase.codigo) {
			case 1..4: return 1
			case 5: return 4
			case 6: return 8
			case 7: return 2
			case 8: return 5
			case 9: return 3
			case 10: return 1
			case 11: return 7
			case 12: assert false
			default: assert false
		}
	}
	
	boolean seSolapaCon(AsignacionUNGS that) {
		if (!seSolapaCon(that.diaSemana, that.horaDesde, that.horaHasta))
			return false
		return seSolapaConFechas(that.fechaDesde, that.fechaHasta)		
//		return seSolapaCon(that.fechaDesde, that.diaSemana, that.horaDesde, that.horaHasta)
	}

	boolean seSolapaConFechas(Date fechaInicio, Date fechaFin) {
		return fechaInicio <= this.fechaHasta && this.fechaDesde <= fechaFin 
	}	

	boolean seSolapaCon(Date fecha, Intervalo iv) {
		if 	(!seSolapaCon(iv))
			return false
		return this.fechaDesde <= fecha && fecha <= this.fechaHasta
	}
			
	@Deprecated
	boolean seSolapabaCon(Date fechaDesde, Date fechaHasta, Id dia, Date horaDesde, Date horaHasta) {
		// Si no caen el mismo día de la semana ya está
		if (this.diaSemanaUNGS != dia)
			return false;
		// Si no se solapan las fechas desde-hasta, listo
		if (this.fechaHasta < fechaDesde || fechaHasta < this.fechaDesde)
			return false;
		// Si no se solapan las horas desde-hasta, listo
		if (this.horaHasta <= horaDesde || horaHasta <= this.horaDesde)
			return false;
			
		// Finalmente, se solapan :p
		return true;
	}

	boolean comparteComisionCon(AsignacionUNGS a) {
		return !comisiones.disjoint(a.comisiones)
	}
	
	boolean minimizarCambiosDeAula() {
		// NOTE: deberia venir en la configuracion la indicacion de si se quiere
		// respetar el aula para toda la universidad o solo para el CAU.
		return cau
//		return true
	}

	@Override int compareTo(AsignacionUNGS that) { this.id - that.id }
	
}
