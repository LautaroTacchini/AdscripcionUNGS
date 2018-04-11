package aulas.prepro

import aulas.Aula
import aulas.Clase
import aulas.PreAsignacion
import aulas.Problema;
import ungs.Id

class ConflictoPreasignacionesNoDisjuntas extends Conflicto {
	
	ConflictoPreasignacionesNoDisjuntas(Set<List> overlapping) {
		// No se admite emptyList ni null como causa de un Conflicto
		assert overlapping && overlapping.every { it }
		elements['overlapping'] = overlapping
	}
	
	String overlapMsg(Aula a, Collection<Clase> s) {
		return 'Aula: ' + a + '\n' + 
				'Materias: \n' +
				' - ' + 
				s.join('\n - ')
	}
	
	String toString() {
		return 'Se encontraron preasignadas varias materias conflictivas a una misma aula: \n' +
				elements['overlapping'].collect { overlapMsg(it[0], it[1]) }.join('\n\n')
	}
}
	
