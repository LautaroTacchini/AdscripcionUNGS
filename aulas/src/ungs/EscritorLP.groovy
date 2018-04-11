package ungs;

import aulas.*

public class EscritorLP extends aulas.EscritorLP {

	def funcionObjetivo(out) {
		println "- funcion objetivo... "
		out << "Maximize " << endl
		int i = 0
		problema.clases.each { asig ->  

			out << " - ${100 * asig.cantidadInscriptos} ${Z(asig)}"
			if (++i % 5 == 0)
				out << endl;
			
			problema.aulas.findAll { asig.puedeUsar(it) }.each { aula ->
				int pts = asig.puntaje(aula)
	
				if (pts != 0) { // Cambiar cuando esté hecho el puntaje
					out << (i > 1? " + " : "") + pts + " " + X(asig, aula) +
							((i++ % 5 == 0)? endl : "")
				}
			}
		}
		
		out << endl
		i = 0
		
		problema.clases.each { asig ->
			problema.clases.findAll { asig2 ->
				asig < asig2 && tienenVariableY(asig, asig2)
			}.each { asig2 ->
				out << " - " + Y(asig, asig2) + ((++i % 5 == 0)? endl : "") 
			}
		}
		out << endl
	}

	@Override	
	def restricciones(out) {
		super.restricciones(out)
		coherenciaY(out)
	}
	
	@Override	
	def desigualdadesValidas(out) {
		comisionesP2(out)
	}

	
	def aulasCompatiblesPorClique(out) {
		println "- restricciones de aulas compatibles (por cliques)... "
		
		Set cliquesGeneradas = [];
		
		problema.clases.each { AsignacionUNGS it ->
			revisarCliques(it.fechaDesde, it.minutoInicial(), problema, out, cliquesGeneradas)
			revisarCliques(it.fechaDesde, it.minutoFinal(), problema, out, cliquesGeneradas)
			
			revisarCliques(it.fechaHasta, it.minutoInicial(), problema, out, cliquesGeneradas)
			revisarCliques(it.fechaHasta, it.minutoFinal(), problema, out, cliquesGeneradas)
		}
		
//		problema.clases.each {
//			Date horaDesdeMasUno = it.horaDesde.clone()
//			horaDesdeMasUno[Calendar.MINUTE] += 1
//			revisarCliques(it.fechaDesde, it.fechaDesde, it.diaSemanaUNGS, it.horaDesde, horaDesdeMasUno, problema, out, cliquesGeneradas)
//			revisarCliques(it.fechaHasta, it.fechaHasta, it.diaSemanaUNGS, it.horaDesde, horaDesdeMasUno, problema, out, cliquesGeneradas)
//			
//			Date horaHastaMenosUno = it.horaHasta.clone()
//			horaHastaMenosUno[Calendar.MINUTE] -= 1
//			revisarCliques(it.fechaDesde, it.fechaDesde, it.diaSemanaUNGS, horaHastaMenosUno, it.horaHasta, problema, out, cliquesGeneradas)
//			revisarCliques(it.fechaHasta, it.fechaHasta, it.diaSemanaUNGS, horaHastaMenosUno, it.horaHasta, problema, out, cliquesGeneradas)
//		}
//		out << endl
	}
	
	def revisarCliques(Date fecha, Intervalo iv, Problema p, out, cliquesGeneradas) {
		// Busco las asignaciones que tocan ese momento
		Set<Clase> cliqueAsignaciones = p.clases.findAll {
//			it.seSolapaCon(fechaDesde, fechaHasta, diaSemana, horaDesde, horaHasta)
			it.seSolapaCon(fecha, iv)
		}
		assert(cliqueAsignaciones.size() > 0)
		
		if (cliqueAsignaciones.size() < 2)
			return;

		if (cliquesGeneradas.contains(cliqueAsignaciones))
			return;
		
		cliquesGeneradas << cliqueAsignaciones;
		
		// Armo el conjunto de aulas asignables a estas asignaciones
		Set<Aula> aulasAsignables = p.aulas.findAll { aula ->
			cliqueAsignaciones.any { it.puedeUsar(aula) }
		}
		
		aulasAsignables.each{ aula ->
			Set<Clase> miniclique = cliqueAsignaciones.findAll { it.puedeUsar(aula) };
			if (miniclique.size() > 1)
				out << miniclique.collect{ X(it, aula) }.join(" + ") << " <= 1" << endl;
			
		}
	}
	
	@Deprecated
	def revisarCliques(Date fechaDesde, Date fechaHasta, Id diaSemana, Date horaDesde, Date horaHasta, Problema p, out, cliquesGeneradas) {
		// Busco las asignaciones que tocan ese momento
		Set<Clase> cliqueAsignaciones = p.clases.findAll {
			it.seSolapaCon(fechaDesde, fechaHasta, diaSemana, horaDesde, horaHasta)
		}
		
		assert(cliqueAsignaciones.size() > 0)
		
		if (cliqueAsignaciones.size() < 2)
			return;

		if (cliquesGeneradas.contains(cliqueAsignaciones))
			return;
		
		cliquesGeneradas << cliqueAsignaciones;
		
		// Armo el conjunto de aulas asignables a estas asignaciones
		Set<Aula> aulasAsignables = p.aulas.findAll { aula ->
			cliqueAsignaciones.any { it.puedeUsar(aula) }
		}
		
		aulasAsignables.each{ aula ->
			Set<Clase> miniclique = cliqueAsignaciones.findAll { it.puedeUsar(aula) };
			if (miniclique.size() > 1)
				out << miniclique.collect{ X(it, aula) }.join(" + ") << " <= 1" << endl;
			
		}
	}
	
	def coherenciaY(out) {
		println "- restricciones de conservacion de aula... "
		problema.clases.each { c1 ->
			problema.clases.findAll { c2 ->
				c1.id != c2.id && tienenVariableY(c1, c2)
			}.each { AsignacionUNGS c2 ->
				problema.aulas.findAll {
					c1.puedeUsar(it) && c2.puedeUsar(it)
				}.each { AulaUNGS it ->
					out << "coherenY_${c1.id}_${c2.id}_${it.id.codigo}: "
					out << X(c1, it) + " - " + X(c2, it) + " - "
					out << ((c1.id < c2.id)? Y(c1, c2) : Y(c2, c1))
					out << " <= 0" + endl
				}
			}
		}
		out << endl
	}
	
	def comisionesP2(out) {
		println "- DVs de comisiones P2..."
		problema.clases.findAll {
			// Estas asignaciones habitualmente son clases teóricas de una 
			// materia con teóricas y prácticas
			it.comisiones.size() > 1
		}.each { AsignacionUNGS teo ->
			problema.clases.findAll {
				tienenVariableY(teo, it)
				// a lo mejor conviene buscar las asignaciones que aparecen en
				// el conjunto de comisiones.  
			}.each { AsignacionUNGS p1 -> // en general esta asignación es una práctica
				problema.clases.findAll { AsignacionUNGS p2 -> // otra supuesta práctica
					p1 < p2 &&
					tienenVariableY(teo, p2) &&
					p1.seSolapan(p2)
					// TODO: pensar qué pasa si los tipos de aula requeridos por
					// cada tipo de aula son diferentes.
				}.each { AsignacionUNGS p2 ->
					out << "comisionesP2_${teo.id}_${p1.id}_${p2.id}: "
					out << Y(teo, p1) << " + " << Y(teo, p2) << " >= 1" << endl
				}
			}
		}
		out << endl
	}
	
	@Override	
	def tiposVariables(out) {
		super.tiposVariables(out)
		// Variables Y
		problema.clases.each { AsignacionUNGS a1 ->
			problema.clases.findAll { a2 ->
				a1 < a2 && tienenVariableY(a1, a2)
				// TODO: Pensar qué pasa si a1 y a2 tienen distinto tipo de aula.
			}.each { AsignacionUNGS a2 ->
				out << Y(a1, a2) << endl
			}
		}
	}
		
	
	// cositas auxiliares ......................................................
	
	// TODO: ¿Debería preguntarse si ambas asignaciones requieren un mismo tipo
	// de aula? ¿Qué pasa si una asignación necesita un labo y la otra no?
	boolean tienenVariableY(AsignacionUNGS a1, AsignacionUNGS a2) {
		return a1 != a2 &&
			(a1.minimizarCambiosDeAula() || a2.minimizarCambiosDeAula()) && // Vale el OR (pensarlo :p)
			!a1.seSolapan(a2) &&
			a1.comparteComisionCon(a2);
	}

	String X(AsignacionUNGS c, AulaUNGS a) { "X_" + c.id + "_" + a.id.codigo }
	
	String Y(AsignacionUNGS i, AsignacionUNGS j) { Y(i.codigo, j.codigo) }
	String Y(int i, int j) {
		if (i >= j)
			return Y(j,i)
		return "Y_" + i + "_" + j
	}
	
}
