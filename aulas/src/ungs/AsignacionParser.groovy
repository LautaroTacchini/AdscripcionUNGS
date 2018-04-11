package ungs

import aulas.*

class AsignacionParser extends Parser<AsignacionUNGS> {

	static enum Header { ANIO_ASIGNACION, PERIODO_LECTIVO,
		CODIGO_COMISION, NOMBRE_COMISION, CODIGO_ASIGNACION,
		FECHA_DESDE, FECHA_HASTA, CODIGO_DIA_SEMANA, NOMBRE_DIA_SEMANA,
		HORA_DESDE, HORA_HASTA, CODIGO_TIPO_CLASE, NOMBRE_TIPO_CLASE,
		CANTIDAD_INSCRIPTOS
	}
	// Ver http://antblah.blogspot.com/2009/09/utf-8-problems-and-subversion.html
	// Esto se supone que esta en utf-8.
	String header = "año,periodo lectivo,comisión,nombre," +
			"asignación,fecha_desde,fecha_hasta," +
	// Esto parece estar en cp1252 (windows).
//	String header = "a�o,periodo lectivo,comisi�n,nombre," +
//            "asignaci�n,fecha_desde,fecha_hasta," +
            "dia de semana,nombre dia de semana,hs desde,hs hasta," +
            "tipo de clase,nombre de tipo de clase,cant  de inscriptos"
	
	AsignacionParser() { super() }
			
//	boolean headerOk(String text) { return text == header }
	void validateHeader(String text) { assert text == header }
	
	AsignacionUNGS parse(String str) {
		Map tokens = slice(Header.class, str)
		String periodo = tokens[Header.PERIODO_LECTIVO]
		boolean esCAU = periodo.contains("CAU")
		int codigoComision = tokens[Header.CODIGO_COMISION].toInteger()
		Id comision = new Id(codigoComision, tokens[Header.NOMBRE_COMISION])
		int codigo = tokens[Header.CODIGO_ASIGNACION].toInteger()
		// fecha_desde,fecha_hasta
		// 08/15/2011,11/26/2011
		// see java.text.SimpleDateFormat
		String dateFormat = "MM/dd/yyyy"
		Date fechaDesde = Date.parse(dateFormat, tokens[Header.FECHA_DESDE])
		Date fechaHasta = Date.parse(dateFormat, tokens[Header.FECHA_HASTA])
		// TODO: definir constructor y mover las validaciones ahí.
		assert(fechaDesde <= fechaHasta)
		// NOTE: sería bueno que los días y horarios válidos se definieran en 
		// 		una configuración externa.
		// NOTE: según los datos, domingo == 1, sábado == 7
		int codigoDia = tokens[Header.CODIGO_DIA_SEMANA].toInteger()
		Id dia = new Id(codigoDia, tokens[Header.NOMBRE_DIA_SEMANA])
		// hs desde,hs hasta
		// 08:00:00,12:00:00
		String timeFormat = "HH:mm:ss"
		Date horaDesde = Date.parse(timeFormat, tokens[Header.HORA_DESDE])
		Date horaHasta = Date.parse(timeFormat, tokens[Header.HORA_HASTA])
		// NOTE: sería bueno definir en una configuración externa la máxima 
		// 		cantidad permitida de inscriptos y la duración mínima por 
		//		asignación. Podría ir a parar a un preproceso.
		assert(horaDesde <= horaHasta)
		int codigoTipoClase = tokens[Header.CODIGO_TIPO_CLASE].toInteger()
		Id tipoClase = new Id(codigoTipoClase, tokens[Header.NOMBRE_TIPO_CLASE])
		int inscriptos = tokens[Header.CANTIDAD_INSCRIPTOS].toInteger()	

		return new AsignacionUNGS(periodo, esCAU, codigo, [comision] as Set,
			fechaDesde, fechaHasta, horaDesde, horaHasta, dia, tipoClase, 
			inscriptos)
	}
	
	Set<AsignacionUNGS> addToContext(AsignacionUNGS a, Set<AsignacionUNGS> asignaciones) {
		AsignacionUNGS preexisting = asignaciones.find { it.id == a.id}
		if (preexisting) {
			preexisting.comisiones += a.comisiones
			preexisting.cantidadInscriptos += a.cantidadInscriptos
			return asignaciones
		} else {
			return asignaciones << a
		}
	}
	
}
