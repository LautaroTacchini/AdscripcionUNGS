package exactas

import aulas.*

class Exactas {

	public static Setup setup
	
	// NOTA: la inicialización por separado de la definición de variables ayuda
	// a detectar problemas, que de otra forma quedan enmascarados.
	static {
		try {
			setup = new Setup(
					new AulaParser(),
					new ClaseParser(),
					new PreAsignacionParser(),
					new SolucionParser(),
					new SolucionInterpreter(),
					new EscritorLP(),
//					new EscritorLPXRepresentantesMaxImpacto(),
					new GeneradorDeReporteExactas()
				) 
		} catch (Throwable t) {
			t.printStackTrace()
			throw t
		}
	}
	
}
