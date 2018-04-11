package aulas.prepro

import aulas.Clase
import aulas.PreAsignacion
import aulas.Problema;
import ungs.Id


/**
 * Valida que estén definidas las aulas de las preasignaciones.
 *
 * Detecta problemas de datos en los que se viole el invariante de que toda 
 * preasignación se corresponda con una aula disponible. Cuando puedan 
 * definirse aulas reservadas (no asignables) habrá que modificar acá; 
 * mientras tanto se debería mostrar una advertencia y no mucho más para 
 * cada violación detectada por esta clase.
 * 
 * @return En caso de encontrarse un Conflicto devuelve el detalle de lo
 * 		sucedido; si no devuelve {@code null}.
 */
class AulasPreasignadasExistentesValidator implements Validator {

	Conflicto validate(Problema pb) {
		
		Set<Id> missing = pb.preasignadas.findAll { pre ->
//			int asignacion, aula // según Preasignacion
//			println "IdClase: ${pre.asignacion}, IdAula: ${pre.aula}"
			// FIXME: hoy hay que hacer cosas feas para  obtener la clase a
			// partir de su id (el casteo y la búsqueda lineal). ¿Será que en el
			// problema convenga de alguna manera tener el conjunto de aulas y
			// un map de Ids a objetos?
//			Clase c = pb.clases.find { it.id == pre.asignacion.toString() }
			!pb.aulas.find { it.id == pre.aula.toString() }
		}
		return !missing? null: new ConflictoAulasPreasignadasInexistentes(missing)
	}
	

}
