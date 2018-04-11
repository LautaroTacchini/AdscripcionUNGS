package aulas

// Algo de subclases fáciles de generar: 
// http://blogs.jetbrains.com/idea/2011/04/quick-prototyping-in-groovy-with-convert-map-to-class-intention/

abstract class Aula implements Comparable<Aula> {
	def id
	int capacidad

	int compareTo(Aula that) {
		return this.id.hashCode() - that.id.hashCode()
	}

	String toString() {
		properties.findAll { !(it.key in ["class", "metaClass"])  }
	}
	
}
