package ungs

import aulas.Setup

class UNGS {

	static Setup setup = new Setup(
			new AulaParser(), 
			new AsignacionParser(), 
			new PreAsignacionParser(), 
			new SolucionUNGSParser(),
			new SolucionUNGSInterpreter(),
			new EscritorLP(),
			new GeneradorDeReporteUNGS()
		)
	
}
