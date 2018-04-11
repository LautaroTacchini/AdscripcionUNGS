package aulas.prepro

import aulas.Aula;
import aulas.Clase;
import aulas.Intervalo;
import ungs.Id

class Conflicto {
	
	Map<String, Object> elements = [:]
	String msg
	String toString() { return msg }
}

class ConflictoCliqueClasesSinAulas extends Conflicto {
	Set<Clase> clique
	Set<Aula> asignables
	Intervalo iv
	
	ConflictoCliqueClasesSinAulas(Intervalo t, Set<Clase> clases, Set<Aula> aulas) {
		clique = clases
		asignables = aulas
		iv = t
		msg = String.format("[intervalo: %s, #clases: %d, #aulas: %d]",
			t, clique.size(), asignables.size())
	}
}

//	class ConflictoPocosInscriptos : public Conflicto	{
//		public: 	ConflictoPocosInscriptos(const Asignacion &a);
//		private:	Asignacion asignacion;
//	};
//	
//	class ConflictoAsignacionSinAulaAsignable : public Conflicto {
//		public:		ConflictoAsignacionSinAulaAsignable(const Asignacion &a);
//		private: 	Asignacion asignacion;
//	};
