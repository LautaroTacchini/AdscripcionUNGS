package aulas.prepro

import aulas.Aula
import aulas.Clase
import aulas.PreAsignacion
import aulas.Problema;
import ungs.Id

class ConflictoPreasignacionesInfactibles extends Conflicto {
	
//	Set<Id> ids
	
//	ConflictoPreasignacionesInfactibles(Set<Id> ids, Problema pb) {
	ConflictoPreasignacionesInfactibles(Set infeasibilities) {
		// No se admite emptySet ni null como causa de un Conflicto
		assert infeasibilities
		elements['infeasibilities'] = infeasibilities
	}
	
	String toString() {
		return "Se encontraron preasignaciones con aulas inválidas para las materias: " + 
				'\n' + elements['infeasibilities'].join('\n')
	}
}
	
