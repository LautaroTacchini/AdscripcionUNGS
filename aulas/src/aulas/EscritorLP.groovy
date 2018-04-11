package aulas;

abstract class EscritorLP {

	Problema problema

//  Formato LP:
//	Anything that follows a backslash (\) is a comment and is ignored until a 
//	return is encountered. Blank lines are also ignored.

	def escribirLP(String filename) {
		println "Escribiendo formulacion..."
		new File(filename).withWriter { out ->
			funcionObjetivo(out)
			out << endl << "Subject to" << endl
			restricciones(out)
			desigualdadesValidas(out)
			tiposVariables(out)
			out << endl << "End" << endl
		} 
	}
	
	abstract def funcionObjetivo(out)
		
	def restricciones(out) {
		unAulaPorClase(out)
		//aulasCompatiblesPorAristas(out)
		aulasCompatiblesPorClique(out)
		// NOTA: la 'coherenciaY' o 'preservar aula entre clases de un mismo
		// curso' podría plantearse como un caso común a todos los problemas de
		// asignacion de aulas, e independiente de los atributos de las clases;
		// sin embargo, hasta que madure un poco más la implementación de 
		// Exactas y tengamos mejor idea de cómo generalizar este problema, 
		// temporariamente pasa a considerarse una característica de la UNGS.
//		coherenciaY(out)
		preasignadas(out)
	}
	
	def desigualdadesValidas(out) {
		// NOTA: con igual criterio que con 'coherenciaY' por el momento las
		// comisionesP2 se consideran una característica de la UNGS.
//		comisionesP2(out)
	}

	// Descripción de las variables:
	// X_{i,j}: indica si la clase 'i' es asignada al aula 'j'.
	// Z_i:		indica si *NO* pudo asignarse aula a la clase 'i'.
	
		
	def unAulaPorClase(out) {
		println "- restricciones de un aula por clase..."
		
		// Para 'i' una Clase y 'j' un Aula asignable a 'i', o bien 
		// 1) a 'i' se le asigna un único 'j' o 
		// 2) 'i' se queda sin asignar.	
		problema.clases.each { c ->
//			out << "UnAulaPorClase_" + c.id + ": ";
			def aulasUsables = problema.aulas.findAll { c.puedeUsar(it) }
			out << aulasUsables.collect{ X(c, it) }
					.join(" + ") + " + " + Z(c) + " = 1" + 
//					";" +
					endl
		}
		out << endl;
	}
	
	def aulasCompatiblesPorAristas(out) {
		throw new UnsupportedOperationException('Estaba implementada para la ' +
			'Sarmiento y no fue adaptada aún para otros casos.')
		
		println "- restricciones de aulas compatibles (por aristas)..."
	
		// No se puede asignar una misma aula a dos clases si
		// 1) las clases se solapan y 2) ambas podrían usar el aula. 		
	
		problema.clases.each { Clase c1 ->
			def aulasA1 = problema.aulas.findAll { c1.puedeUsar(it) }
			problema.clases.findAll{ c2 -> 
				c1.id < c2.id && c1.seSolapan(c2) 
			}.each { Clase c2 ->
				aulasA1.findAll { c2.puedeUsar(it) }.each { Aula it ->
					// NOTA: un único '<<' reduce el tiempo de ejecución.
					out << String.format("aulasComp_%d_%d_%d: %s + %s <= 1\n",
//					out << String.format("aulasComp_%d_%d_%d: %s + %s <= 1;\n",
							c1.id, c2.id, it.id.codigo, 
							X(c1, it), X(c2, it))
				}
			}
		}
		out << endl
	}
	
	def aulasCompatiblesPorClique(out) {
//		println "- restricciones de aulas compatibles (por cliques)... "
		println "- restricciones de aulas compatibles... "
		
		Set<Clase> cliquesGeneradas = [], cliqueClases

		// TODO: estas restricciones todavía no tienen label en la formulación.		
//		out << "UnaClasePorCliqueEnCadaAula..."
		// las etiquetas sería bueno que indiquen el momento del tiempo, para 
		// simplificar el debugging.
		problema.clases.each { c ->
			cliqueClases = problema.clases.findAll { it.seSolapaCon(c.minutoInicial()) }
			assert(cliqueClases.size() > 0) // toda clase debe ser parte de su clique.
			unaClasePorCliqueEnCadaAula(cliqueClases, cliquesGeneradas, out)
			
			cliqueClases = problema.clases.findAll { it.seSolapaCon(c.minutoFinal()) }
			assert(cliqueClases.size() > 0) // toda clase debe ser parte de su clique.
			unaClasePorCliqueEnCadaAula(cliqueClases, cliquesGeneradas, out)
		}
		out << endl
	}
	
	def unaClasePorCliqueEnCadaAula(Set<Clase> clases, Set cliquesGeneradas, out) {
		
		if (clases.size() <= 1 || cliquesGeneradas.contains(clases))
			return

		cliquesGeneradas << clases
	
		// Armo el conjunto de aulas asignables a estas clases
		Set<Aula> aulasAsignables = problema.aulas.findAll { aula ->
			clases.any { it.puedeUsar(aula) }
		}

		aulasAsignables.each { aula ->
			Set<Clase> miniclique = clases.findAll { it.puedeUsar(aula) }
			if (miniclique.size() > 1) {
				out << miniclique.collect{ X(it, aula) }.join(" + ") << " <= 1" 
//				out << ";" 
				out << endl;
			}
		}

	}
	
	def preasignadas(out) {
		println "- restricciones de preasignacion... (" + problema.preasignadas.size() + ")" 
		problema.preasignadas.each {
//			out << "preasignada_${it.asignacion}_${it.aula}: "
			// TODO: 'asignacion' -> 'clase'
			out	<< X(it.asignacion, it.aula) << " = 1" 
//			out << ";";
			out << endl;
		}
		out << endl;
	}
	
	def tiposVariables(out) {
		println "- variables..."
		// Son todas binarias
		out << "Integer " << endl;
	
		// Variables X y Z
		problema.clases.each {
			out << Z(it)
			out << endl;
			problema.aulas.findAll { aula ->
				it.puedeUsar(aula)
			}.each { aula ->
				out << X(it, aula)
				out << endl;	
			}
		}
//		out << ";"
		out << endl;
	}
		
	
	// cositas auxiliares ......................................................
	
	def endl = "\n"
	
	String X(Clase c, Aula a) { "X_" + c.id + "_" + a.id }
	String Z(Clase c) { "Z_" + c.id }
	
	String X(int c, String a) { "X_" + c + "_" + a }
	
}
