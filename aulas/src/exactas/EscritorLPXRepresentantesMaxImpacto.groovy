package exactas;

import ungs.AsignacionUNGS;
import aulas.*

class EscritorLPXRepresentantesMaxImpacto extends aulas.EscritorLP {

	PuntuadorExactas punt = new PuntuadorExactas()
	
	String X(Clase c1, Clase c2) { "X_" + c1.id + "_" + c2.id } // nuevo para el modelo x representantes
	
	def funcionObjetivo(out) {
		println "- función objetivo... "
		out << "Maximize " << endl
		int i = 0
		
		// uv es una arista
		// E_h son las aristas del grafo H que representa el "impacto" que se quiere maximizar;
		// en nuestro problema, H representa a las variables Y de querer compartir aula.
		// Esta formulación sigue el modelo x representantes
		// x_{uv} significa que u representa a v, o que ambas clases irían a una misma aula.
		// Por ahora la formulación x representantes no toma en cuenta la totalidad de las
		// restricciones. En particular, falta atender las de capacidad de las aulas antes de
		// considerar el resto de los detalles. 
		// max sum_{uv in E_h, u<v} x_{uv}
		problema.clases.each { c1 ->
			problema.clases.findAll { c2 ->
				c1.id < c2.id && tienenVariableY(c1, c2)
			}.each { c2 ->
				out << " + " + X(c1, c2) + cendl(++i)
			}
		}
		out << endl
	}

	boolean N(Clase c1, Clase c2) {
		return c1.seSolapaCon(c2.minutoInicial()) || c1.seSolapaCon(c2.minutoFinal()) ||
				c2.seSolapaCon(c1.minutoInicial()) || c2.seSolapaCon(c1.minutoFinal())
	} 
	
		
	@Override
	def restricciones(out) {
//		super.restricciones(out)
//			unAulaPorClase(out)
//			aulasCompatiblesPorClique(out)
//			preasignadas(out)
		println "- restricciones... "
		println "1: xuu + sum{v in !N-(u)} x_{vu} >= 1 forall u in V, [${new Date()}]"
		// st 1: xuu + sum{v in !N-(u)} x_{vu} >= 1 forall u in V
		problema.clases.each { c1 ->
			out << X(c1, c1)
			def sum =  problema.clases.findAll { c2 ->
				c1.id > c2.id && !N(c1, c2)
			}.collect { Clase c2 ->
				X(c2, c1)
			}.join(" + ")
			if (sum)
				out << " + " + sum
			out << " >= 1" << endl
		}
		
		println "2: xuv + xuw <= 1 forall u in V, vw in Eg, {v, w} in !N+(u), [${new Date()}]"
		// st 2: xuv + xuw <= 1 forall u in V, vw in Eg, {v, w} in !N+(u)
		problema.clases.each { u ->
			def notNplus = problema.clases.findAll {  
				u.id < it.id && !N(u, it)
			}
			notNplus.each { Clase v ->
				notNplus.findAll { N(v, it) }.each { Clase w ->
					out << X(u, v) + " + " + X(u, w) + " <= 1" + endl
				}
			}
		}
		
		
		// st 3: sum_{u in V} xuu <= C
		println "3: sum_{u in V} xuu <= C, [${new Date()}]"
		
		// [una propuesta para intentar capturar el tamaño de las aulas...
		//		sum{tam(u) <= tam} xuu <= sum{t>=tam} #aulas(t) forall tam ]
		// [otro problema importante y pendiente es resolver los casos no factibles de la mejor
		//	forma posible; son los que ahora tienen su Z == 1]
		
	}

	@Override
	def tiposVariables(out) {
//		super.tiposVariables(out)
		println "- variables..."
		// Son todas binarias
		out << "Integer " << endl;
		problema.clases.each { c1 ->
			problema.clases.findAll{ c2 ->
				c1.id == c2.id ||
				(c1.id < c2.id && !N(c1, c2))
			}.each { c2 ->
				out << X(c1, c2) + endl
			}
		}
		
		
		// Variables Y
//		problema.clases.each { ClaseExactas c1 ->
//			problema.clases.findAll { c2 ->
//				c1 != c2 && tienenVariableY(c1, c2)
//				// TODO: Pensar qué pasa si a1 y a2 tienen distinto tipo de aula.
//			}.each { ClaseExactas c2 ->
////				out << Y(c1, c2) << endl
//				out << ((c1.id < c2.id)? Y(c1, c2) : Y(c2, c1)) << endl
//			}
//		}
	}

	
	// cositas auxiliares ......................................................
	
	/** Conditional EndOfLine. */
	String cendl(int i) { return (i % 5 == 0 ? endl : "") }

	// TODO: ¿Debería preguntarse si ambas asignaciones requieren un mismo tipo
	// de aula? ¿Qué pasa si una asignación necesita un labo y la otra no?
	boolean tienenVariableY(ClaseExactas c1, ClaseExactas c2) {
		return c1 != c2 &&
			(c1.minimizarCambiosDeAula() || c2.minimizarCambiosDeAula()) && // Vale el OR (pensarlo :p)
			!c1.seSolapaCon(c2.minutoInicial()) && !c1.seSolapaCon(c2.minutoFinal()) &&
			!c2.seSolapaCon(c1.minutoInicial()) && !c2.seSolapaCon(c1.minutoFinal()) &&
			c1.comparteComisionCon(c2);
	}

	String Y(ClaseExactas i, ClaseExactas j) { Y(i.id as Integer, j.id as Integer) }
	String Y(int i, int j) {
		if (i >= j)
			return Y(j,i)
		return "Y_" + i + "_" + j
	}

}
