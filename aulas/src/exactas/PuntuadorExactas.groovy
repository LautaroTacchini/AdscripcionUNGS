package exactas

import aulas.*;

class PuntuadorExactas extends Puntuador {	

	PuntuadorExactas() {
		super(); 
		puntajes["alumnoConCambioDePabellon"] = 1;
		puntajes["pizarronMenos"] = 10;
		puntajes["alumnoExtraCapacidad"] = 1;
	}
	
	// TODO: documentar acá todas las cosas que se tienen en cuenta, para
	//		facilitar la lectura desde otros lados.
	int puntaje(ClaseExactas c, AulaExactas a) {
		// Cambios de pabellon
		int puntos = (c.pabellonPreferido == 0?  0 : 
						(a.codigoPabellon == c.pabellonPreferido ? 0 
							: -puntajes["alumnoConCambioDePabellon"] * c.cantidadInscriptos))
				
		// Pizarrones menos de los pedidos
		puntos -= (c.minimoDePizarrones <= a.pizarrones? 0 : 
						c.minimoDePizarrones - a.pizarrones)*puntajes["pizarronMenos"]
		
		// Capacidad del aula excedida
		puntos -= (c.cantidadInscriptos <= a.capacidad? 0 : 
						c.cantidadInscriptos - a.capacidad)*puntajes["alumnoExtraCapacidad"] 
		
		// confiamos en que los inscriptos quepan en la capacidad
		int exceso = a.capacidad / ((c.cantidadInscriptos + 2) / 2)
		if (3 <= exceso)
			puntos -= exceso - 3
		// -14547 
						
		return puntos 
	}
	
}
