import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Solicitud {
	
	private String materia;
	private String dia;
	
	private int hrIni;
	private int hrFin;
	
	static String[] arrayDias = {"domingo","lunes","martes","miercoles","jueves","viernes","sabado"};
	static Set<String> dias = new HashSet<>(Arrays.asList(arrayDias));
	
	static boolean horaValida(int hr) {
		return 0<=hr && hr<=23;
	}
	
	// Representa la solicitud de una materia para ser dictada un dia
	// en especifico con una horar de inicio y de fin.
	public Solicitud(String materia, String dia, int hrIni, int hrFin){
		this.materia = materia;
		
		assert dias.contains(dia);
		this.dia = dia;
		
		assert horaValida(hrIni);
		this.hrIni = hrIni;
		
		assert horaValida(hrFin);
		this.hrFin = hrFin;
	}
	
	public String getMateria() {
		return materia;
	}

	public String getDia() {
		return dia;
	}

	public int getHrIni() {
		return hrIni;
	}

	public int getHrFin() {
		return hrFin;
	}
	
	@Override
	public String toString() {
		String ret = "";
		ret = materia + dia + hrIni + hrFin;
		return ret;
	}
}
	
