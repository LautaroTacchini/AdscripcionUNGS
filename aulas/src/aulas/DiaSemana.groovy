package aulas;

public enum DiaSemana { 
	DOMINGO, LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO;
	
	static DiaSemana parse(String str) {
		return valueOf(str.toUpperCase().tr("ÁÉÍÓÚ", "AEIOU"))
	}
}
