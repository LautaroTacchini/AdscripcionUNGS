package exactas;

import ungs.AsignacionUNGS;
import aulas.*

class EscritorLP extends aulas.EscritorLP {

	PuntuadorExactas punt = new PuntuadorExactas()
	
	def funcionObjetivo(out) {
		punt.pb = problema // FIXME: no gana un concurso de belleza tener punt a medio inicializar
		println "- funcion objetivo... "
		out << "Maximize " << endl
		int i = 0
		problema.clases.each { c ->
			// se asume que puntajePorNoAsignar(c) <= 0 forall c
			int penalidad = punt.puntajePorNoAsignar(c)
			if (penalidad < 0) 
				out << " $penalidad ${Z(c)}" + cendl(++i)
			// NOTA: esta expresión que sigue indica que dejar una clase sin 
			// aula, es decir que la variable Z correspondiente a la clase valga
			// uno, descuenta una cantidad de 'puntos' igual a 100 * la cantidad
			// de inscriptos a la clase.
//			out << " - ${100 * c.cantidadInscriptos} ${Z(c)}" + cendl(++i)
		}
		i = 0
		// el bloque de abajo se separa para facilitar el mantenimiento.
		problema.clases.each { c ->
			problema.aulas.findAll {
				// TODO: cuando una materia está preasignada no debería considerarse acá.
				c.puedeUsar(it) && punt.puntaje(c, it) != 0
			}.each { 
				// cuando no es cero, el puntaje es negativo.
				// TODO: sería bueno aclarar que este puntaje incluye los
				//		cambios de pabellón, las preferencias de cantidad de
				//		pizarrones y vaya uno a saber cuánta cosa más. 
				//		Una alternativa es que eso se pueda leer claramente dde
				//		la documentación de "puntaje".
				int penalidad = punt.puntaje(c, it)				
				out << " $penalidad ${X(c, it)}" + cendl(++i)
			}
		}
		out << endl
		
		problema.clases.each { Clase c1 ->
			problema.clases.findAll { Clase c2 ->
				// TODO: pensar cómo tienen que tratarse las preasignaciones.
				c1.id < c2.id && tienenVariableY(c1, c2)
			}.each { Clase c2 ->
				// Este cálculo de penalidad debería ir PuntuadorExactas.
				int penalidad = (0.5*(c1.cantidadInscriptos + c2.cantidadInscriptos)*0.10) + 1 
				out << " - $penalidad " + Y(c1, c2) + cendl(++i)
				//out << " - " + Y(c1, c2) + cendl(++i)
			}
		}
		out << endl
	}
	
	
	@Override
	def restricciones(out) {
		super.restricciones(out)
		coherenciaY(out)
	}

	
	def coherenciaY(out) {
		println "- restricciones de conservacion de aula... "
		problema.clases.each { c1 ->
			problema.clases.findAll { c2 ->
				// no está buenísimo que una clase tenga variable Y consigo misma.
				c1.id != c2.id && tienenVariableY(c1, c2)
			}.each { ClaseExactas c2 ->
				problema.aulas.findAll {
					c1.puedeUsar(it) && c2.puedeUsar(it)
				}.each { AulaExactas it ->
					out << "coherenY_${c1.id}_${c2.id}_${it.id}: "
					out << X(c1, it) + " - " + X(c2, it) + " - "
					// TODO: assert lo de abajo == Y(c1, c2), ahora que Y() ordena los ids.
					out << ((c1.id < c2.id)? Y(c1, c2) : Y(c2, c1))
					out << " <= 0" + endl
				}
			}
		}
		out << endl
	}

	@Override
	def tiposVariables(out) {
		super.tiposVariables(out)
		
		// TODO: evitar las variables Z (pedido no asignado) para las materias preasignadas.
		
		// Variables Y
		problema.clases.each { ClaseExactas c1 ->
			problema.clases.findAll { c2 ->
				c1 != c2 && tienenVariableY(c1, c2)
				// TODO: Pensar qué pasa si a1 y a2 tienen distinto tipo de aula.
			}.each { ClaseExactas c2 ->
//				out << Y(c1, c2) << endl
				out << ((c1.id < c2.id)? Y(c1, c2) : Y(c2, c1)) << endl
			}
		}
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
