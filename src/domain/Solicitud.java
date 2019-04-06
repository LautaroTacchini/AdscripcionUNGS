package domain;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Solicitud {
	
	private String materia;
	private String dia;
	
	// TODO REEMPLAZAR POR CLASE HORARIO.
	private Horario hrIni;
	private Horario hrFin;
	
	static String[] arrayDias = {"Domingo","Lunes","Martes","Mi�rcoles","Jueves","Viernes","S�bado"};
	static Set<String> dias = new HashSet<>(Arrays.asList(arrayDias));
		
	// Representa la solicitud de una materia para ser dictada un dia
	// en especifico con una horar de inicio y de fin.
	public Solicitud(String materia, String dia, Horario hrIni, Horario hrFin){
		this.materia = materia;
		
		assert dias.contains(dia);
		this.dia = dia;
		this.hrIni = hrIni;
		this.hrFin = hrFin;
	}
	
	public String getMateria() {
		return materia;
	}

	public String getDia() {
		return dia;
	}

	public Horario getHrIni() {
		return hrIni;
	}

	public Horario getHrFin() {
		return hrFin;
	}
	
	@Override
	public String toString() {
		String ret = "";
		ret = materia + dia + hrIni + hrFin;
		return ret;
	}
}
