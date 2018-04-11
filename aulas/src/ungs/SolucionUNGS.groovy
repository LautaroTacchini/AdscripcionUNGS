package ungs

class Pair extends groovy.util.MapEntry {
	Pair(Object key, Object value) { super(key, value) }
}

class SolucionUNGS extends aulas.Solucion {
	
//	Map<Integer, Integer> resueltas // Asignacion -> Aula
//	Set<Integer> noResueltas
	Set<Pair> conAulasDistintas
	
	SolucionUNGS(resueltas, noResueltas, conAulasDistintas) {
		super(resueltas, noResueltas)
//		this.resueltas = resueltas
//		this.noResueltas = noResueltas
		this.conAulasDistintas = conAulasDistintas
	}
	
}
