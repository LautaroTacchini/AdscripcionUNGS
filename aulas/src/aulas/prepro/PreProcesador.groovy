package aulas.prepro

import static java.util.Calendar.*
import aulas.Aula;
import aulas.Clase;
import aulas.Intervalo;
import aulas.PreAsignacion;
import aulas.Problema;
//import aulas.Config.SpanishString;
import aulas.Config;

import ungs.AsignacionUNGS
import ungs.Id;

class PreProcesador {

	ConfigObject config

//	Contexto ctx
	
	Set<Conflicto> advertencias(Problema pb) {
		List<Class<Validator>> validadores = [
		]
		return validadores.inject([]) { result, it ->
			Conflicto c = it.newInstance().validate(pb)
			return c? result << c: result
		}
	}
	
	// acá debería definirse la factibilidad
	Set<Conflicto> errores(Problema pb) {
		
//		Conflicto conflicto = validarClasesDefinidas(pb)
		List<Class<Validator>> validadores = [
			// TODO: al encontrar preasignaciones con aulas o clases no definidas se generan
			// 		variables no booleanas y MIP.java no genera resultado.txt.pure, rompiendo 
			//		todo. Por ahora se consideran ambas situaciones como errores pero sería
			//		más robusto poder saltearlas.
			ClasesPreasignadasExistentesValidator.class,
			AulasPreasignadasExistentesValidator.class,
			
			// TODO: no estaría mal que en la configuración se puedan elegir qué validadores se
			//		toman como errores, como advertencias o se ignoran.
			
			PreasignacionesFactiblesValidator.class,
			PreasignacionesDisjuntasValidator.class
		]
		
		return validadores.inject([]) { result, it ->
			Conflicto c = it.newInstance().validate(pb)
			return c? result << c: result
		}
		
	}
	
		
	Problema procesar(Problema p) {
		Set<Aula> aulas = p.aulas
		Set<Clase> clases = p.clases
		Set<PreAsignacion> preasignadas = p.preasignadas
	
		clases = procesarClasesSinInscriptos(clases)
		clases = procesarClasesSinAulaUsable(clases, aulas)
		clases = procesarHorarioDeAsignaciones(clases)

		return new Problema(aulas, clases, preasignadas)
	}
	
	// NOTA: en los preprocesamientos y revisaciones sería bueno tener la opción 
	// de guardar en una memoria de trabajo los resultados parciales que puedan
	// servir más adelante. Por ejemplo, el cómputo de las cliques de clases 
	// se usa tanto en las revisaciones como al escribir las formulaciones. 
	// También, a partir de las revisaciones pueden surgir cotas inferiores que
	// sirvan en la formulación. A primera vista parece mejor andar pasando un 
	// contexto que manejarse con variables globales. Por otro lado, sería bueno 
	// que no haya repetición de código y que el cómputo de cliques no se 
	// implemente dos veces.
	
	Set<Conflicto> revisar(Problema pb) {
		Set<Conflicto> result = []
		// NOTE: Agregar a la configuracion valores que indiquen cuales chequeos hacer y cuales no.
		result << revisarCliques(pb)
		
		// Verifico si hubo conflictos
		//    if (!conflictos.empty()) {
		//        list<Conflicto>::iterator conf = conflictos.begin();
		//        while (conf != conflictos.end()) {
		//            cerr << conf->texto() << endl;
		//            ++conf;
		//        }
		//    }
		
		return result
	}
	
	// preprocesos... ----------------------------------------------------------
	
	def procesarClasesSinInscriptos(clases) {
		clases.findAll {  
			!toBoolean(config.eliminarAsignacionesSinInscriptos) || 
				 it.cantidadInscriptos > 0 
			}
	}
	
	def procesarClasesSinAulaUsable(clases, aulas) {
		clases.findAll {
			!toBoolean(config.eliminarAsignacionesSinAulaUsable) ||
			aulas.any { a -> it.puedeUsar(a) } 
		}
	}

	def procesarHorarioDeAsignaciones(clases) {
		clases.collect {
			toBoolean(config.redondearHorarios)? horaRedondeada(it): it
		}
	}
	
	// revisaciones... ---------------------------------------------------------
	
	// Informa si en un instante hay más pedidos de clases que aulas disponibles.
	// Sirve para saber de antemano que hay un límite a la calidad de la solución.
	def revisarCliques(Problema pb) {
		// Si acá se toman por separado los scan(inicial) y los scan(final)
		// sería posible devolver una solución más significativa. Por ejemplo,
		// en vez de: 
		//[intervalo: [MIERCOLES, 18:00, 18:01], #clases: 32, #aulas: 31]
		//[intervalo: [JUEVES, 18:00, 18:01], #clases: 34, #aulas: 33]
		//[intervalo: [MIERCOLES, 18:59, 19:00], #clases: 32, #aulas: 31]
		//[intervalo: [JUEVES, 18:29, 18:30], #clases: 34, #aulas: 33]
		// devolver:
		//[intervalo: [MIERCOLES, 18:00, 19:00], #clases: 32, #aulas: 31]
		//[intervalo: [JUEVES, 18:00, 18:30], #clases: 34, #aulas: 33]

		def result = scan(pb, { Clase c -> c.minutoInicial() }) +
				scan(pb, { Clase c -> c.minutoFinal() })
		
		return result
		
//		return scan(pb, { Clase c -> c.minutoInicial() }) +
//				scan(pb, { Clase c -> c.minutoFinal() })
	}
	
	// Esta de acá abajo es una vieja imple que habría que borrar cuando los
	// to-do estén resueltos arriba para el caso de la UNGS.
	// TODO: en realidad debería fijarse por las asignaciones con igual tipo de
	// aula asignable, que no son todas las que coincidan en horario.
	// TODO: arreglar para que el momento sea con fechaDesde y hasta.
	// TODO: Mantener un historial de cliques para no repetir las mismas (ver 
	//     ejemplo en el EscritorLP)	
	def revisarCliquesEnMomento(Id diaSemana, Date fecha, Date hora, Problema p) {
		// Busco las asignaciones que tocan ese momento
		
		Set<Clase> cliqueAsignaciones = p.clases.findAll {
			it.seSolapaCon(diaSemana, fecha, hora)
		}
		
		// Armo el conjunto de aulas asignables a estas asignaciones
		Set<Aula> aulasAsignables = p.aulas.findAll { aula ->
			cliqueAsignaciones.any { it.puedeUsar(aula) }
		} 
		
		// Comparamos y reportamos errores
		if (cliqueAsignaciones.size() > aulasAsignables.size())
			return [ new ConflictoCliqueClasesSinAulas(cliqueAsignaciones, aulasAsignables, diaSemana, fecha, hora) ];
		else
			return []
	}
	
		
	// funciones auxiliares... -------------------------------------------------
	
	// TODO: buscar un mejor nombre.
	def scan(Problema pb, Closure getter) {  			
		Set<Intervalo> intervalosRevisados = []
		pb.clases.findAll { c ->
			// add() devuelve true cuando elem no pertenecía al cj. 
			intervalosRevisados.add(getter.call(c))
		}.collect { Clase c -> 
			revisarIntervalo(getter.call(c), pb)
		}.findAll { it != null }
	}
	
	def revisarIntervalo(Intervalo iv, Problema pb) {
		Set<Clase> cliqueClases = pb.clases.findAll { it.seSolapaCon(iv) }
		// Sobreaproximación de aulas asignables a estas clases.
		Set<Aula> aulasAsignables = pb.aulas.findAll { aula ->
			cliqueClases.any { Clase it -> it.puedeUsar(aula) }
		}
		// Comparamos y reportamos errores
		if (cliqueClases.size() > aulasAsignables.size()) 
			return new ConflictoCliqueClasesSinAulas(iv, cliqueClases, aulasAsignables)
		else
			return null
	}
	
	Boolean toBoolean(String conditionValue) {
		use (Config.SpanishString) {
			return conditionValue.toBoolean()
		}
	}

	@Deprecated	
	Date minutosEnCero(Date hora) {
		// NOTA: por algún motivo no funciona nada de lo que está comentado, así 
		// que lo que se usa resultó lo mejorcito que se encontré funcionando.
//		result.horaDesde[Calendar.MINUTE] = 0
//		result.horaDesde.putAt(Calendar.MINUTE, 0)
//		result.horaDesde.setMinutes(0)
//		Calendar cal = result.horaDesde.toCalendar()
//		cal.set(Calendar.MINUTE, 0);
//		result.horaDesde.setTime(cal.getTimeInMillis())
//		return hora.parse("HH:mm", hora.format("HH") + ":00")
		Date result = hora.parse("HH:mm", hora.format("HH") + ":00")
		return result
	}
	
	@Deprecated
	Date aumentarHora(Date hora) {
		// NOTA: se sigue la tónica minutosEnCero.
		int h = hora.format("HH").toInteger() + 1
		return hora.parse("H:mm", h.toString() + ":00")
	}
	
	Clase horaRedondeada(Clase it) {
		
		assert it.class == AsignacionUNGS.class
//		Asignacion result = it.clone()

//		boolean flag = false		
//		if (it.horaDesde[MINUTE] != 0) {
//			println "begin: " + it
//			println it.horaDesde.format("HH:mm")
//			flag = true
//		}
		
		Date horaDesde = it.horaDesde
		// NOTA: está bien el < estricto en horaDesde y no estricto en horaHasta
		if (30 < horaDesde[MINUTE])
			horaDesde[HOUR]++
//			horaDesde = aumentarHora(horaDesde)
		horaDesde[MINUTE] = 0
//		horaDesde = minutosEnCero(horaDesde)

		Date horaHasta = it.horaHasta
					
		if (30 <= horaHasta[MINUTE])
			horaHasta[HOUR]++
//			horaHasta = aumentarHora(horaHasta)
		horaHasta[MINUTE] = 0
//		horaHasta = minutosEnCero(horaHasta)

		return new AsignacionUNGS(it.periodoLectivo, it.cau, it.id, it.comisiones, 
			it.fechaDesde, it.fechaHasta, it.horaDesde, it.horaHasta, 
			it.diaSemanaUNGS, it.tipoClase, it.cantidadInscriptos)
	}

}
