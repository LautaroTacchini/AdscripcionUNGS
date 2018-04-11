package aulas.prepro

import aulas.PreAsignacion
import ungs.Id

class ConflictoAulasPreasignadasInexistentes extends Conflicto {
	
	ConflictoAulasPreasignadasInexistentes(Set<PreAsignacion> pas) {
		// No se admite emptySet ni null como causa de un Conflicto
		assert pas
		elements['missing'] = pas
		msg = "Se detectaron preasignadas las aulas " +
				pas.collect { it.aula } + " pero no se encontró su definición. " +
				"Para resolver este inconveniente se pueden definir las aulas " +
				"que están faltando, o bien modificar las preasignaciones " + 
				pas + "."
	}

//	// TODO: auch! acá se habla de corregir la preasignación pero falta 
//	// información de contexto que aclara de cuál se trata en particular. 	
}
	
