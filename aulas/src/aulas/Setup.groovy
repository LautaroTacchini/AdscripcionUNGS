package aulas

import aulas.prepro.Conflicto;

class Setup {
	
	Parser<Aula> parserAula
//	Parser<Asignacion> parserAsignaciones
	Parser<Clase> parserClase
	Parser<PreAsignacion> parserPreasigna

	@Deprecated
	SolucionParser solucionParser
	SolucionInterpreter solucionInterpreter
	EscritorLP escritorLP
	GeneradorDeReporte genReporte

	Setup(parserAula, parserClase, parserPreasigna, 
			solucionParser, 
			solucionInterpreter, escritorLP, genReporte) {
		this.parserAula = parserAula
		this.parserClase = parserClase
		this.parserPreasigna = parserPreasigna
		this.solucionParser = solucionParser
		this.solucionInterpreter = solucionInterpreter
		this.escritorLP = escritorLP
		this.genReporte = genReporte
	}
	
			
	// FIXME: no es hermoso que acá en Setup esté la lógica de cómo se lee un problema. Está 
	// faltando el objeto lectorDeProblema para que Setup se limite a una sola responsabilidad,
	// que debería ser asociar a los colaboradores que interactúan juntos para resolver una
	// instancia de una Universidad en concreto. Para mejorar el diseño del Solver estoy
	// queriendo evitar usar setup.leerProblema(...) y en su lugar me gustaría reemplazarlo por
	// def lectorDeProblemas = setup.lector; ... def problema = lectorDeProblemas.leer(...). 
	Problema leerProblema(ConfigObject cfg) {
		def aulas = parserAula.read(cfg.archivo_aulas)
		def clases = parserClase.read(cfg.archivo_asignaciones)
		def preasignaciones = parserPreasigna.read(cfg.archivo_preasignadas)
		
		return new Problema(aulas, clases, preasignaciones)
	}	

	// TODO: no es hermoso que el problema se asigne a esta altura xq es difícil de debuggear.
	//		Sería más prolijo recibirlo en el constructor del escritor, o al escribir el LP. 
	void escribirLP(Problema p, String filename) {
		escritorLP.problema = p
		escritorLP.escribirLP(filename)
	}	
	
	
	Solucion interpretarSolucion(Map<String, Object> sol) {
		return solucionInterpreter.interpret(sol)
	}
	
	@Deprecated
	Solucion leerSolucion(String filename) {
		throw new RuntimeException("No longer a suported operation!")
		return solucionParser.parse(filename)
	}
	
	Reporte generarReporte(Problema pb, Set<Conflicto> cfts, Solucion sol) {
		return genReporte.generar(pb, cfts, sol)
	}
	
	// TODO: pensar cómo hacer para poder escribir
	// 		escribir(problema).en("aulas.lp") 
	// desde Solver.solve()

	// Algo sobre Fluent Interfaces en groovy
	// http://www.nofluffjuststuff.com/home/video?id=56
	// http://groovy.329449.n5.nabble.com/Fluent-Interface-AST-tt3330066.html#none
	
	// Algo sobre Dependency Injection:
	// http://groovy.329449.n5.nabble.com/Dependency-injection-in-Groovy-td372725.html
	// http://blog.stannard.net.au/2010/05/18/dependency-injection-with-groovy-and-google-guice/
		
}
