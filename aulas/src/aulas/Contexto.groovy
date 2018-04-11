package aulas

import aulas.prepro.Conflicto

/** Asociación de todas las partes del problema.
 * La intención de esta clase es agrupar todos los elementos que surgen a lo 
 * largo del procesamiento de la intancia, de modo que a cada paso le sea 
 * suficiente con recibir un único parámetro.
 * Más allá de aprovechar la información de tipado, no es espera agregar otra
 * inteligencia, ni lógica.
 */
class Contexto {

	// Al final no está clara la conveniencia de usar esto. Cuando quede más 
	// claro convendría justificarlo un poco más. Por ahora, al intentar usarlo
	// se agrega ruido en el Solver.
	
	ConfigObject config
	Problema problema
	Set<Conflicto> errores
		
}
