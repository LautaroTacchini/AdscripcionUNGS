import aulas.*
import exactas.*

Solver.solve(Exactas.setup, args)

// Dudas: ...
// Pendientes: 
// - Cambiar en la hoja de 'pedidos' el orden de las columnas; de 
//   'Pab, Pizarrón, Aula' a 'Pab, Aula, Pizarrón'.
// - Agregar horarios de comienzo y fin al resultado. Facilita la lectura.
// - Agregar en config.txt o aulas.bat la opción para configurar el tiempo de 
//   ejecución.
// - Cuando una restricción es infactible, xej xq hubo preasignación manual con
//   problemas inadvertidos, sería bueno que apareciera un mensaje más o menos
//   explicativo, en vez del actual "infeasible row 18502: 0 > 1".
// - Mejorar la clase MIP para que maneje problemas enteros de una forma 
//   diferente y se puedan detectar los casos en que faltan definir variables
//   enteras. También podríaz ser que en vez de enteros se acepten sólo binarios.

//println "Exactas"
