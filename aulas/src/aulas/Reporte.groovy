package aulas

import aulas.prepro.Conflicto;

class Reporte {

	Map resumen
	Map detalle
//	Map alumnosPorClaseNoAsignada
	
	def endl = "\n"

	Reporte(resumen, detalle) {
		this.resumen = resumen
		this.detalle = detalle
	}
		
	String getPrettyDetalle() {
		return detalle.collect { key, value ->
			'-' * 32 + endl + key + endl + value.collect { k, v -> "$k=$v"}.join(endl) 
		}.join(endl*2)
	}
	
	// -- yaml --
	// Loading:
	// Yaml yaml = new Yaml();
	// Object obj = yaml.load("a: 1\nb: 2\nc:\n  - aaa\n  - bbb");
	// System.out.println(obj);
	//
	//	{b=2, c=[aaa, bbb], a=1}
	//
	// Dumping:
	// Map<String, String> map = new HashMap<String, String>();
	// map.put("name", "Pushkin");
	// Yaml yaml = new Yaml();
	// String output = yaml.dump(map);
	// System.out.println(output);
	// ---
	// name: Pushkin
	
}

class GeneradorDeReporte {
	
	Clase clase(Problema pb, id) {
		assert pb.clases.isEmpty() || pb.clases.find { true }.id.class == id.class
		Clase result = pb.clases.find { it.id == id }
		assert result != null
		return result
	}
	
	Aula aula(Problema pb, id) {
		assert pb.aulas.isEmpty() || pb.aulas.find { true }.id.class == id.class
		Aula result = pb.aulas.find { it.id == id }
		assert result != null
		return result
	}
	
	Reporte generar(Problema pb, Set<Conflicto> cfs, Solucion sol) {
		Map resumen = generarResumen(pb, cfs, sol)
		Map detalle = generarDetalle(pb, cfs, sol)
		return new Reporte(resumen, detalle)
	}

	Map generarResumen(Problema pb, Set<Conflicto> cfs, Solucion sol) {
		Map result = [:]
		result['clasesAsignadas'] = sol.asignadas.size() 
		result['clasesNoAsignadas'] = sol.noAsignadas.size()

		def alumnosAsignados = sol.asignadas.inject(0) { sum, it ->
			sum += clase(pb, it.key)?.cantidadInscriptos
		}
		result['alumnosAsignados'] = alumnosAsignados
				
		def alumnosNoAsignados = sol.noAsignadas.inject(0) { sum, it ->

			// LISTO! el problema de abajo está resuelto. La solución se ve en 
			// aulas.EscritorLP.aulasCompatiblesPorClique().
			/* algo está andando mal porque no está asignando POO al aula 2 del
			 * pab 1 mientras que está vacía en ese horario.		
			 */
/*			Clase c = clase(pb, it)
//			if (c.diaSemana == DiaSemana.LUNES)
//				println "id: ${c.id}\t #alu: ${c.cantidadInscriptos}\tdde: ${c.horaDesde}\thta: ${c.horaHasta}"
			// la materia "Programación Orientada a Objetos", 19-22hs, 37 alumnos, no tiene aula.
			if (c.id == "22") {
				println c
				println '-' * 64
//				pb.aulas.findAll { c.puedeUsar(it) }.each { println it }
				// el aula "1002", la 2 del pab 1, es una de las que no se le asignó
				def otras = sol.asignadas.findAll { k, v -> v == "1002" } // clases asignadas al aula 1002
//				otras.each { println it }
				// estas son las clases que están asignadas los lunes al aula 2 del pab 1.
				otras.findAll { k, v -> clase(pb, k).diaSemana == DiaSemana.LUNES }
						.each { k, v -> println clase(pb, k) }
				println '-' * 64
				// esto de abajo está dando vacío, indicando que no hay clases
				// asignadas en el horario de POO al aula 2 del pab 1.
				otras.each { k, v ->
					def that = clase(pb, k)
					if (c.seSolapaCon(that))
						println that
				}
			}
*/			
			sum += clase(pb, it)?.cantidadInscriptos
		}
		result['alumnosNoAsignados'] = alumnosNoAsignados
		
		return result
	}
	
	Map generarDetalle(Problema pb, Set<Conflicto> cfs, Solucion sol) {
		Map result = [:]
		// NOTE: pensar si conviene separar el detalle de los conflictos del de
		// la solución.	
		result += detalleClasesNoAsignadas(pb, sol)
		return result
//		Todavía falta listar conflictos.
	}

	Map detalleClasesNoAsignadas(Problema pb, Solucion sol) {
		Map alumnosPorClaseNoAsignada = new TreeMap()
		sol.noAsignadas.each {
			alumnosPorClaseNoAsignada[it] = clase(pb, it)?.cantidadInscriptos
		}
		return  ['alumnosPorClaseNoAsignada' : alumnosPorClaseNoAsignada]
	}


	
} 

