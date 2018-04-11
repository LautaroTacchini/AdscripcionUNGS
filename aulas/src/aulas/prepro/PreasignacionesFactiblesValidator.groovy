package aulas.prepro

import aulas.Aula
import aulas.Clase;
import aulas.PreAsignacion
import aulas.Problema;
import ungs.Id

class PreasignacionesFactiblesValidator implements Validator {

	/**
	 * Valida factibilidad de las preasignaciones.
	 *
	 * Detecta preasignaciones en las que el aula no se corresponde con los requisitos de la
	 * clase a la que se le asigna. Principalmente detecta problemas de asignación manual. 
	 * @return En caso de encontrarse un Conflicto devuelve el detalle de lo
	 * 		sucedido; en caso contrario devuelve {@code null}.
	 */
	Conflicto validate(Problema pb) {
		// TODO: ojo que si una clase o aula no existe acá se puede romper todo. Estaría bien
		//		si hubiera algo así como precondiciones para un Validator, algo que evite este
		//		problema. Una idea es a lo ANT, que se invocan las precondiciones antes que esto
		//		pero ahí habría que evitar que una misma validación se corra varias veces; mejor
		//		sería encontrar algo más simple.
		// FIXME: hoy hay que hacer cosas feas para  obtener la clase a
		// partir de su id (el casteo y la búsqueda lineal). ¿Será que en el
		// problema convenga de alguna manera tener el conjunto de aulas y
		// un map de Ids a objetos? Quizas mejor sea que la preasignación
		// incluya de verdad una clase y un aula, en vez de sólo sus Ids.
		Set infeasible = pb.preasignadas.findAll { pre ->
			Clase c = pb.clases.find { 
				it.id == pre.asignacion.toString()
			}
//			assert c != null
			Aula a = pb.aulas.find {
				it.id == pre.aula
			}
//			assert a != null
			// en {Clases/Aulas}PreasignadasExistentesValidator debería detectarse si a == null 
			// o c == null.  
			if (c == null || a == null)
				return false
			return !c.puedeUsar(a)
		}.collect { PreAsignacion pre ->
			Clase c = pb.clases.find { it.id == pre.asignacion.toString() }
			Aula a = pb.aulas.find { it.id == pre.aula }
			// FIXME: tengo el problemita que como Clase es algo abstracto entonces no puedo 
			//		obtener de forma genérica su nombre y, xlo tanto, acá no puedo armar un 
			//		lindo cartelito que diga (ponele) "Análsis II tiene 104 inscriptos pero el
			//		aula 8 del pab 2 tiene sólo 80 lugares disponibles.". Buscarle solución.
			return [a, c]
		}
		// TODO: estaría bueno tener un Map<Clase, Aula> en vez de un Set<List>>
//		println infeasible // debug
		
		return !infeasible? null: new ConflictoPreasignacionesInfactibles(infeasible)
	}
	

}
