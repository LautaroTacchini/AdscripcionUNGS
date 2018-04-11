package exactas

import aulas.*
import aulas.prepro.Conflicto;

class ReporteExactas extends Reporte {
			
	ReporteExactas(resumen, detalle) {
		super(resumen, detalle)
	}

	def separador = '-' * 40 + endl
	
	String getPrettyDetalle() {
		String result = ""
		result += getPrettyDetalleClasesNoAsignadas()
		result += getPrettyDetalleClasesCambiadasDePabellon()
		return result
	}
	
	String getPrettyDetalleClasesNoAsignadas() {
		String result = separador
		Map info = detalle['alumnosPorClaseNoAsignada']
		if (info.isEmpty())
			result += 'No hay clases sin asignar.' + endl
		else
			result += 'Clases sin asignar:' + endl +
//					'Clase | #alumnos' + endl +
//					info.collect { k, v ->
////						k + ' | ' + v.join(' | ')
//					k + ' | ' + v
//					}.join(endl) + endl
					'Clase\t#alumnos' + endl +
					info.collect { k, v -> k + '\t' + v }.join(endl) + endl
		return result
	}

	String getPrettyDetalleClasesCambiadasDePabellon() {
		String result = separador
		Map info = detalle['cambioPabellon']
		if (info.isEmpty())
			result += 'No hay clases cambiadas de pabellón.' + endl
		else result += 'Clases cambiadas de pabellón:' + endl +
					'IdClase\t#alumnos\tdia\tdesde\thasta\tpref.\taula\tNombreClase' + endl +
					info.sort { it.key.id.toInteger() } 
						.collect { ClaseExactas c, AulaExactas a ->
//						"id: $c.id, pabPref: $c.pabellonPreferido, pabAsig: $a.codigoPabellon"
						"$c.id\t$c.cantidadInscriptos\t$c.diaSemana\t$c.horaDesde\t$c.horaHasta\t$c.pabellonPreferido\t$a.id\t$c.asignatura"
					}.join(endl) + endl
		return result
	}
	
}

// ------------------------------------

class GeneradorDeReporteExactas extends GeneradorDeReporte {
	
	Reporte generar(Problema pb, Set<Conflicto> cfs, Solucion sol) {
		// TODO: pensar cómo evitar la repetición de código con GenRepo.generar()
		assert sol.class == Solucion.class
		Map resumen = generarResumen(pb, cfs, sol)
		Map detalle = generarDetalle(pb, cfs, sol)
		return new ReporteExactas(resumen, detalle)
	}
	
	Map generarDetalle(Problema pb, Set<Conflicto> cfs, Solucion sol) {
		Map result = super.generarDetalle(pb, cfs, sol)
		
		Map cambioPabellon = 
				sol.asignadas.findAll { k, v ->
					clase(pb, k).pabellonPreferido != aula(pb, v).codigoPabellon
				}.collectEntries { k, v ->
					[clase(pb, k), aula(pb, v)]
				}
				
		result['cambioPabellon'] = cambioPabellon

		return result
	}

}
