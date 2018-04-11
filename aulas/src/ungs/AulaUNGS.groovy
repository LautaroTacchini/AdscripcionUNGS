package ungs

import aulas.Aula

// Algo de subclases fáciles de generar: 
// http://blogs.jetbrains.com/idea/2011/04/quick-prototyping-in-groovy-with-convert-map-to-class-intention/

class AulaUNGS extends Aula {
	Id edificio
	Id id
	boolean activa
	int capacidad
	Id tipo

	int compareTo(Aula that) {
		assert that.class == AulaUNGS.class
		return this.id.codigo - that.id.codigo 
	}
	
}
