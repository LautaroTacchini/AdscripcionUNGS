import aulas.*
import ungs.*

/*
+ Códigos de asignación en el archivo de datos aparecen repetidos, pero con distintas comisiones,
  horarios, inscriptos, etc.
- Se trata de asignaciones compartidas entre varias comisiones. Se suman las cantidades de inscriptos
  y los conjuntos de comisiones asociados a cada asignación.

+ Aulas "PASIVAS" y cantidad de inscriptos == 0 para algunas asignaciones.
- Se eliminan de los respectivos conjuntos de candidatos. Sería bueno reportar por separado las
  asignaciones sin inscriptos.

+ Asignaciones con cantidad de inscriptos que no entran en ninguna aula (labos en particular).
- Se informan por separado y se eliminan del problema a resolver.

+ Cliques para las que no alcanzan las aulas.
- Se informan por separado, *no* se modifica la instancia y se optimiza lo que queda.

+ Edificios preferidos/no preferidos.
- En principio es sólo para el CAU: módulo preferido = 3; módulo no preferido = 1. Volver a preguntar.

+ Aulas preferidas.
- Todavía no está implementado.

+ Tipo de asignación sin definir.
- Por defecto va a aula tipo 1.

+ Tipo de asignación = 12.
- Se supone que no deberíamos recibirlo.

+ Poco alumnos.
- Con menos de 4 se va a hablar con el docente.

*/

Solver.solve(UNGS.setup, args)

// FIXME: no está del todo claro qué hará el preprocesador, ya que en la
//		UNGS pasa de 6000 a 222500 puntos de penalidad cuando deja de usarse.
// UNGS con preprocesador:
//		Asignaciones resueltas=781
//		Asignaciones no resueltas=26
//		Asignaciones con cambio de aula=0
//		Comisiones resueltas=467
//		Comisiones con asignacion no resuelta=24
//		Comisiones con cambio de aula=0
//		Alumnos sin asignar=60
//		Alumnos con cambio de aula=0
// UNGS sin preprocesador:
//		Asignaciones resueltas=781
//		Asignaciones no resueltas=52
//		Asignaciones con cambio de aula=0
//		Comisiones resueltas=429
//		Comisiones con asignacion no resuelta=69
//		Comisiones con cambio de aula=0
//		Alumnos sin asignar=2225
//		Alumnos con cambio de aula=0

//println "UNGS"
