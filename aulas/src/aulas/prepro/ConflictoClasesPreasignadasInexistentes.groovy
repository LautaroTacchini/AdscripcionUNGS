package aulas.prepro

import ungs.Id

class ConflictoClasesPreasignadasInexistentes extends Conflicto {
	
//	Set<Id> ids
	
	ConflictoClasesPreasignadasInexistentes(Set<Id> ids) {
		// No se admite emptySet ni null como causa de un Conflicto
		assert ids
		elements['missingIds'] = ids
	}
	
	String toString() {
		return "Se encontraron preasignadas las clases/asignaciones " +
				elements['missingIds'] + " pero no se encontró su definición. " +
				"Verifique que esas preasignaciones corresponden a " +
				"clases/asignaciones existentes."
	}
}
	
