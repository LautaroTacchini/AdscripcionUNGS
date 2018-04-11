package aulas.prepro

import aulas.Aula
import aulas.Clase;
import aulas.PreAsignacion
import aulas.Problema;
import ungs.Id

class PreasignacionesDisjuntasValidator implements Validator {

	/**
	 * Valida el no solapamiento de las preasignaciones.
	 *
	 * Detecta preasignaciones donde pedidos conflictivos se asignen a una misma aula.
	 * Principalmente detecta problemas de asignación manual.
	 * @return En caso de encontrarse un Conflicto devuelve el detalle de lo
	 * 		sucedido; en caso contrario devuelve {@code null}.
	 */
	Conflicto validate(Problema pb) {
//		Set infeasible = pb.preasignadas.findAll { pre ->
//		println 'preasignadas: ' + pb.preasignadas
		Set<List> conflicting = pb.preasignadas.collect { pre ->
			println 'pre: ' + pre
			Clase c = pb.clases.find {
				it.id == pre.asignacion.toString()
			}
			assert c != null
			def overlapping = pb.preasignadas.findAll { other ->
				if (other <= pre || pre.aula != other.aula) 
					return false
//				println 'clases: ' + pb.clases
				println 'other: ' + other
				Clase d = pb.clases.find { 
					it.id == other.asignacion.toString()
				}
				assert d != null
				c.seSolapaCon(d)
			}
			if (!overlapping)
				return null
			
			Aula a = pb.aulas.find { it.id == pre.aula }
//			println 'c: ' + c // debug
//			println 'a: ' + a // debug
//			println 'overlapping: ' + overlapping // debug
			overlapping = overlapping.collect { PreAsignacion other ->
				pb.clases.find { it.id == other.asignacion.toString() } 
			}
			return [a, overlapping + c]
		}.findAll { it != null }

//		println 'conflicting: ' + conflicting // debug
//		conflicting.each { println it } // debug
		
//		return !infeasible? null: new ConflictoPreasignacionesInfactibles(infeasible)
		return !conflicting? null: new ConflictoPreasignacionesNoDisjuntas(conflicting)
	}
	

}
