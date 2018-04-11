package aulas.prepro

import aulas.PreAsignacion
import aulas.Problema;
import ungs.Id

class ClasesPreasignadasExistentesValidator implements Validator {

	/**
	 * Valida que estén definidas las clases de las preasignaciones.
	 *
	 * Sirve para detectar problemas de construcción donde se viole el
	 * invariante de que toda preasignación se corresponda con una clase.
	 * @return En caso de encontrarse un Conflicto devuelve el detalle de lo
	 * 		sucedido; en caso contrario devuelve {@code null}.
	 */
	Conflicto validate(Problema pb) {
		Set<Id> missing = pb.preasignadas.findAll { pre ->
//			int asignacion, aula
//			println "IdClase: ${pre.asignacion}, IdAula: ${pre.aula}"
			// FIXME: hoy hay que hacer cosas feas para  obtener la clase a
			// partir de su id (el casteo y la búsqueda lineal). ¿Será que en el
			// problema convenga de alguna manera tener el conjunto de aulas y
			// un map de Ids a objetos? Quizas mejor sea que la preasignación
			// incluya de verdad una clase y un aula, en vez de sólo sus Ids.
			!pb.clases.find { it.id == pre.asignacion.toString() }
		}
//		println missing // esto va andando bien pero falta definir el conflicto.
		// == [[clase/asignacion: -1, aula: -1], [clase/asignacion: 0, aula: 0]]
		return !missing? null: new ConflictoClasesPreasignadasInexistentes(missing)
	}
	

}
