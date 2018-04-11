package ungs

import aulas.*

class PreAsignacionParser extends Parser<PreAsignacion> {
	
	static enum Header { CODIGO_ASIGNACION, CODIGO_AULA }
		
	PreAsignacionParser() { super(false) }
	
//	boolean headerOk(String text) { true }
	void validateHeader(String text) { true }

	PreAsignacion parse(String str) {
		Map tokens = slice(Header.class, str)
		int codigoAsignacion = tokens[Header.CODIGO_ASIGNACION].toInteger()
		int codigoAula = tokens[Header.CODIGO_AULA].toInteger()
		return new PreAsignacion(asignacion: codigoAsignacion, aula: codigoAula)
	}
	
	Set<PreAsignacion> addToContext(PreAsignacion a, Set<PreAsignacion> pre) {
		assert !pre.find(a)
		return pre << a
	}

}	