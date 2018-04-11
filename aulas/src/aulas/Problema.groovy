package aulas

class Problema {

	Set<Aula> aulas
	/** Universo de todas las clases.
	 * Incluye las preasignadas y las no asignables x cero inscriptos 
	 * */
	Set<Clase> clases
	Set<PreAsignacion> preasignadas

	Problema(def aulas, def clases, def preasignadas) {
		// TreeSet se encarga de que los conjuntos estén ordenados, lo que 
		// simplifica el debugging.
		this.aulas = new TreeSet(aulas)
		this.clases = new TreeSet(clases)
		this.preasignadas = new TreeSet(preasignadas)
//		assert false // probar de agregar preasignaciones!!!
	}
	
	
//	/**
//	* Busca en el problema las clases que solapen con <code>m</code>.</p>
//	* <b>Nota:</b> es importante advertir que para un punto del tiempo debe
//	* indicarse si se trata del instante en que comienza o termina un intervalo
//	* de referencia, dado que las condiciones de borde cambian el resultado.
//	* @param horaComienzo Si es verdadero indica que <code>m</code> se debe
//	* 		interpretar como el comienzo de un intervalo de tiempo. En caso
//	* 		contrario se debe interpretar como el fin de un intervalo.
//	*/
////	Set<Clase> buscarCliques(DiaSemana d, Date horaBase, boolean horaComienzo) {
//	Set<Clase> buscarCliques(Momento m, boolean horaComienzo) {
//		return clases.findAll { it.seSolapaCon(new Intervalo(m, horaComienzo)) }
//	}

	
}
