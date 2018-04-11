package exactas

import aulas.*

class AulaParser extends Parser<Aula> {

	static enum Header { CODIGO_PABELLON, NOMBRE_AULA, CAPACIDAD, UBICACION, 
		TIENE_CAÑON, TIENE_RETRO, PIZARRONES, ASIENTOS, NIVEL }

	String header = "Pabellón,Aula,Capacidad,Ubicación,Cañón,Retro," + 
			"Pizarrones,Asientos,Nivel"

	AulaParser() { super() }

	// TODO: parece convenir pasar la validación de header a la superclase.
//	boolean headerOk(String text) { return text == header }
	void validateHeader(String text) { assert text == header }
	
	@Override
	Set<Aula> read(def filename) {
		return readExcel(filename, "Aulas")
	}

	Aula parse(String str) {
		Map tokens = slice(Header.class, str)
		int codigoPabellon = tokens[Header.CODIGO_PABELLON].toInteger()
		String nombre = tokens[Header.NOMBRE_AULA]
		String id = "$codigoPabellon" + (nombre.isInteger()? 
				nombre.padLeft(3, "0"): nombre) 
		int capacidad = tokens[Header.CAPACIDAD].toInteger()
		assert tokens[Header.TIENE_CAÑON] in ["SI", "NO"]
		boolean tieneCañon = tokens[Header.TIENE_CAÑON] == "SI"
		assert tokens[Header.TIENE_RETRO] in ["SI", "NO"]
		boolean tieneRetro = tokens[Header.TIENE_RETRO] == "SI"
		int pizarrones = 0
	    // NOTE: Por ahora se asume que no decir nada es sinónimo de cero.
		if (tokens[Header.PIZARRONES])
			pizarrones = tokens[Header.PIZARRONES].toInteger()
			
		return new AulaExactas(id: id, capacidad: capacidad, 
			codigoPabellon: codigoPabellon, ubicacion: tokens[Header.UBICACION],
			tieneCañon: tieneCañon, tieneRetro: tieneRetro, 
			pizarrones: pizarrones, asientos: tokens[Header.ASIENTOS], 
			nivel: tokens[Header.NIVEL])
	}
	
	Set<Aula> addToContext(Aula a, Set<Aula> aulas) {
		return (a? aulas << a : aulas) 
//		// Abajo está la imple para la UNGS 
//		// NOTA: si no se descartan las aulas pasivas aparecen varias
//		// (filas) con códigos repetidos.
//		if (!a.activa)
//			return aulas
//		assert !aulas.find {it.id.codigo == a.id.codigo }
//		return aulas << a
	}
	
}
