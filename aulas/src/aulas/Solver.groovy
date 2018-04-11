package aulas

import aulas.prepro.Conflicto;
import aulas.prepro.PreProcesador;

class Solver {
	
	static def workdir = 'tmp/'
	
	// Este método 'static' podría convertirse en sintactic-sugar que haga las cosas bien:
	// 	static def solve(Setup setup, String... args) { new Solver(setup).solve(args) }
	
	Solver(Setup s) {
		// En Setup.leerProblema se explica un problema que estoy teniendo con este renglón.
//		problema = setup.leerProblema(cfg)
		
//		setup.escribirLP(problema, workdir + "aulas.lp")
//		Solucion sol = setup.leerSolucion(workdir + "resultado.txt.pure")
//		Reporte reporte = setup.generarReporte(problema, conflictos, sol)
	}

	
	static def solve(Setup setup, String... args) {

		if (1 < args.length) {
			println "Uso: Solver [config]"
			return
		}
		
		def cfg = Config.load(args? args[0] : 'config.txt')
		
		def ctx = new Contexto()
		ctx.config = cfg			// alternativa a usar cfg directamente.
		
		new File(workdir).mkdir() // si ya existe no hace nada.
		
		// se borran los resultados de una corrida anterior xa evitar interpretarlos como nuevos.
		new File(workdir).eachFile { File f -> f.delete() }
		new File('asignacion.xls').delete()
		// ya no se debería estar generando el 'status.txt'.
//		new File('status.txt').delete() // FIXME: horrible que se genere este archivo.
		
		println 'Leyendo pedidos...'
		Problema problema 
		try { 
			problema = setup.leerProblema(cfg) 	// son alternativas.
			ctx.problema = problema				// son alternativas.
		} catch (Exception e) {
			throw e
		}

		// TODO: pienso que el filtro de cfg.PreProcesador es un poco mágico y 
		// sería mejor esconderlo. Acá sólo pasaríamos 'cfg'.
		println 'Pre-procesando pedidos...' 		
		PreProcesador prepro = new PreProcesador(config: cfg.PreProcesador)
		// TODO: definir si quiero manejar y andar pasando de acá para allá un
		// contexto o un problema y dónde meter los conflictos.
		Set<Conflicto> errores = prepro.errores(problema) 

		if (errores) {
			println "¡ERROR!"
			errores.each { println it }
			return
		}
		Set<Conflicto> advertencias = prepro.advertencias(problema)
		if (advertencias) {
			println "¡CUIDADO!"
			advertencias.each { println it }
//			System.exit(1)
		}
		Set<Conflicto> conflictos = [] as Set  // debug
//		Set<Conflicto> conflictos = prepro.revisar(problema) // habilitar
//		println "debug conflictos - begin"
//		conflictos.each { println it }
//		println "debug conflictos - end"
	
		problema = prepro.procesar(problema)	 
		
		setup.escribirLP(problema, workdir + "aulas.lp") // habilitar
		
		if (!cfg.minutosDeProcesamiento)
			cfg.minutosDeProcesamiento = 10
		
		int timeOut = (cfg.minutosDeProcesamiento as Integer) * 60
			
		// Licencia predeterminada en /usr/ilog/ilm/access.ilm
		// MIP.main("aulas.lp", "resultado.txt") 
//		MIP.main("aulas.lp", "resultado-aulas.txt", "60") // timeout en segundos
//		String status = MIP.solve("aulas.lp", "resultado.txt", 60) // timeout en segundos
//		String status = MIP.solve(workdir + "aulas.lp", workdir + "resultado.txt", timeOut) // timeout en segundos
		Map<String, Object> solution = mip.Solver.solve(workdir + "aulas.lp", timeOut) // timeout en segundos
		// 600 seg: 12046 de penalidad, gap 0.44% (exactas-2012.1)

//		String status = solution.get("#status") // transition
//		if (new File("status.txt").text == "Infeasible")
		
		if (solution.get("#status") == "Infeasible")
			throw new RuntimeException("Solución infactible.")
//		assert (new File("status.txt").text == "Optimal")
		// status puede ser "Feasible" cuando da timeout.
		
		// si no existe resultado.txt.pure puede ser xq haya algo mal definido (xej: aulas 
		// preasignadas eliminadas) y el resultado deje de ser booleano puro.
		// TODO: acá falta actualizar la versión de MIP con lo que estoy usando para la tesis y
		// el proyecto de las muñecas rusas. Después es muy probable que esto quede diferente, 
		// con lo que probablemente no valga mucho la pena devanarse los sesos buscándole una
		// gran solución a un problema que viene de arrastre.
//		Solucion sol = setup.leerSolucion(workdir + "resultado.txt.pure")
		Solucion sol = setup.interpretarSolucion(solution)
		
//		println solution.findAll { String k, v -> k.startsWith("X_160") }
		
//		println 'solution:'
//		solution.each { println it }
		
		Reporte reporte = setup.generarReporte(problema, conflictos, sol)
		// todavía falta listar conflictos
		
		reporte.resumen.each {	println it }
//		reporte.detalle.each { k, v -> println k; v.each { println it } } // debug
//		println reporte.prettyDetalle
		
//		sol.escribirSolucion("asignacion.txt")
		sol.escribirSolucionExcel("asignacion.xls", problema)

		// se borran los resultados de una corrida anterior xa evitar interpretarlos como nuevos.
		new File(workdir).eachFile { File f -> f.delete() }
		new File(workdir).delete()
		
		// TODO: andar borrando archivos al ppio y al final es un espanto; incluso andar
		// borrándolos ya era bien feíto. Una opción menos horrenda pero hta ahí es tener un 
		// try/catch/finally, quizás en MainExactas/MainUNGS.
//		println "Chau"
	}
}
