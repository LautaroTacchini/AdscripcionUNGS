package ungs

import aulas.*
import aulas.prepro.Conflicto;

class ReporteUNGS extends Reporte {

	
	ReporteUNGS(resumen, detalle) {
		super(resumen, detalle)
	}

//	Map comisionesConAsignacionNoResuelta
//	Map asignacionesPorComision
//	Map asignacionesConCambioDeAulaPorComision
//	Map alumnosPorAsignacionNoResuelta
//
//	ReporteUNGS(Problema p, Set<Conflicto> cs, Solucion s) {
//		(problema, conflictos, solucion) = [p, cs, s]
//		
//		asignacionesPorComision = new TreeMap()
//		p.clases.each { Clase it ->
//			it.comisiones.each { comision ->
//				if (!asignacionesPorComision[comision.codigo])
//					asignacionesPorComision[comision.codigo] = []
//				asignacionesPorComision[comision.codigo] += it.id
//			}
//		}
//		asignacionesConCambioDeAulaPorComision = 
//				asignacionesPorComision.findAll { Map.Entry c ->
//					solucion.conAulasDistintas.any {
//						it.key in c.value || it.value in c.value
//					}
//				}
//		def alumnosConCambioDeAula = asignacionesConCambioDeAulaPorComision
//				.inject(0) { result, Map.Entry e ->
//					e.value.each { 
//						result += clase(it).cantidadInscriptos
//					}
//					return result 
//				} 	
//		resumen = [
//				"Asignaciones con cambio de aula" : s.conAulasDistintas.size(),
//				"Comisiones resueltas" : comisiones.size() - comisionesConAsignacionNoResuelta.size(),
//				"Comisiones con asignacion no resuelta" : comisionesConAsignacionNoResuelta.size(),
//				"Comisiones con cambio de aula" : asignacionesConCambioDeAulaPorComision.size(),
//				"Alumnos con cambio de aula" : alumnosConCambioDeAula]
//		
//		alumnosPorAsignacionNoResuelta = new TreeMap()
//		s.noResueltas.each {
//			alumnosPorAsignacionNoResuelta[it] = clase(it)?.cantidadInscriptos
//				
//		}
//	}
//	
//	String getDetalle() { 
//		return detalleComisionesConAulasDiferentes() + endl
//	}
//
//	String detalleComisionesConAulasDiferentes() {
//		if (solucion.conAulasDistintas.isEmpty())
//			return "No hay comisiones con asignaciones en diferentes aulas."
//			
//		return "Comisiones con asignaciones en diferentes aulas:" + endl +
//				"Comision | Asignaciones" + endl +
//			asignacionesConCambioDeAulaPorComision.collect { 
//				it.key + " | " + it.value
//			}.join(endl)
//	}

	def separador = '-' * 40 + endl
	
	String getPrettyDetalle() {
		String result = ""
		result += getPrettyDetalleAsignacionesNoResueltas()
		result += getPrettyDetalleComisionesParcialmenteResueltas()
		return result
	}
	
	String getPrettyDetalleAsignacionesNoResueltas() {
		String result = separador
		Map info = detalle['alumnosPorClaseNoAsignada'] 
		if (info.isEmpty())
			result += 'No hay asignaciones sin resolver.' + endl
		else
			result += 'Asignaciones sin resolver:' + endl +
					'Asignacion | #alumnos | Comisiones' + endl +
					info.collect { k, v -> 
						k + ' | ' + v.join(' | ')
					}.join(endl) + endl
		return result
	}
		
	String getPrettyDetalleComisionesParcialmenteResueltas() {
		String result = separador
		Map info = detalle['detalleComisionesParcialmenteResueltas']
		if (info.isEmpty())
			result += "No hay comisiones con asignaciones sin resolver." + endl
		else
			result += 'Comisiones con asignaciones sin resolver:' + endl +
					'Codigo | Nombre | Asignaciones | Sin resolver' + endl +
					info.collect { k, v -> 
						k + ' | ' + v.join(' | ') 
					}.join(endl) + endl
		return result
	}
		
}
	
class GeneradorDeReporteUNGS extends GeneradorDeReporte {

	Reporte generar(Problema pb, Set<Conflicto> cfs, Solucion sol) {
		// TODO: pensar cómo evitar la repetición de código con GenRepo.generar()
		assert sol.class == SolucionUNGS.class
		Map resumen = generarResumen(pb, cfs, sol)
		Map detalle = generarDetalle(pb, cfs, sol)
		return new ReporteUNGS(resumen, detalle)
	}	
	
	Map generarResumen(Problema pb, Set<Conflicto> cfs, SolucionUNGS sol) {
		Map result = super.generarResumen(pb, cfs, sol)
		result['comisionesConCambioDeAula'] = sol.conAulasDistintas.size()
		return result
	}

	Map generarDetalle(Problema pb, Set<Conflicto> cfs, SolucionUNGS sol) {
		Map result = super.generarDetalle(pb, cfs, sol)

		// Agrega una columna con las comisiones involucradas a la lista de 
		// asignaciones sin resolver. 
		Map info = result['alumnosPorClaseNoAsignada']
		info.each { k, v ->
			info[k] = [v, clase(pb, k).comisiones.collect { it.codigo }]
		} 
		
		// Comisiones de las asignaciones que sobrevivieron al PreProcedor.
		// CodigoComision -> NombreComision
		Map comisiones = new TreeMap()
		pb.clases.each { AsignacionUNGS it ->
			it.comisiones.each { comision ->
				comisiones[comision.codigo] = comision.nombre
			}
		}
		
		// CodigoComision -> NombreComision 
		Map comisionesConAsignacionNoResuelta = new TreeMap()
		sol.noAsignadas.each {
			clase(pb, it).comisiones.each { Id comision ->
				comisionesConAsignacionNoResuelta[comision.codigo] = comision.nombre
			}
		}
		// IdComision -> [ NombreComision, AsignacionesUNGS asociadas, 
		//					AsignacionesUNGS asociadas y sin resolver]
		Map detalleComisionesParcialmenteResueltas =
				comisionesConAsignacionNoResuelta.collectEntries { c ->
					// Ids de AsignacionesUNGS de la comisión.
					Set ac = pb.clases.findAll { AsignacionUNGS a ->
							c.key in a.comisiones.collect { it.codigo }
						}.collect { Clase it -> it.id }
					// Ids de AsignacionesUNGS no asignadas (de la comisión)
					def nac = ac.findAll { it in sol.noAsignadas }
					return [c.key, [c.value, ac, nac]] 
				} 
				
		result['detalleComisionesParcialmenteResueltas'] = 
				detalleComisionesParcialmenteResueltas

		return result
	}
	
}
