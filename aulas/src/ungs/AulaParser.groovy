package ungs

import aulas.*

class AulaParser extends Parser<AulaUNGS> {

	static enum Header { CODIGO_EDIFICIO, NOMBRE_EDIFICIO, 
		CODIGO_AULA, NOMBRE_AULA, ESTADO, CAPACIDAD, 
		CODIGO_TIPO, NOMBRE_TIPO };
	
	String header = "cod edificio,nom edificio,cod aula,nom aula," +
            "activa/pasiva,capacidad,cod tipo aula,nom tipo aula"

//	@Deprecated	
//	AulaParser(def target) { super(target) }
	
	AulaParser() { super() }
			
//	boolean headerOk(String text) { return text == header }
	void validateHeader(String text) { assert text == header }
	
	AulaUNGS parse(String str) {
		Map tokens = slice(Header.class, str)
		int codigoEdificio = tokens[Header.CODIGO_EDIFICIO].toInteger()
		Id edificio = new Id(codigoEdificio, tokens[Header.NOMBRE_EDIFICIO])
		int codigoAula = tokens[Header.CODIGO_AULA].toInteger()
		Id id = new Id(codigoAula, tokens[Header.NOMBRE_AULA])

	    // NOTE: se asume que no decir nada es sinónimo de que el aula esté activa.
		assert(tokens[Header.ESTADO] in ["ACTIVA", "PASIVA", ""])
		boolean activa = tokens[Header.ESTADO] != "PASIVA"
		int capacidad = tokens[Header.CAPACIDAD]?
				tokens[Header.CAPACIDAD].toInteger() : 0
		int codigoTipo = tokens[Header.CODIGO_TIPO].toInteger()
		Id tipo = new Id(codigoTipo, tokens[Header.NOMBRE_TIPO])

		return new AulaUNGS(edificio: edificio, id: id, activa: activa,
			capacidad: capacidad, tipo: tipo)
	}
	
	Set<AulaUNGS> addToContext(AulaUNGS a, Set<AulaUNGS> aulas) {
		// NOTA: si no se descartan las aulas pasivas aparecen varias
		// (filas) con códigos repetidos.
		if (!a.activa)
			return aulas
		assert !aulas.find {it.id.codigo == a.id.codigo }
		return aulas << a
	}
	
}
