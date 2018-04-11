package ungs

class Id implements Comparable<Id> {
	int codigo
	String nombre
	
	Id(int i, String s) { codigo = i; nombre = s } 

	String toString() { "[codigo: $codigo, nombre: $nombre]" }
	
	int compareTo(Id that) { this.codigo - that.codigo }
	
}
