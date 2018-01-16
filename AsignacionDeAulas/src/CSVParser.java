import java.util.List;

public class CSVParser {
	List<Solicitud> solicitudes;
	
	public CSVParser(List<Solicitud> solicitudes) {
		assert solicitudes != null;
		this.solicitudes = solicitudes;
	}
	
	public void parse(String entrada) {
		String[] datos = entrada.split(";");
				
		String nombre = datos[0] + datos[1];
		String dia = datos[2];
		int hrIni = parsearHora(datos, 3);
		int hrFin = parsearHora(datos, 4);
		
		Solicitud s = new Solicitud(nombre,dia,hrIni,hrFin);
		solicitudes.add(s);
	}

	// TODO FIJATE COMO HACER PARA QUE RECIBA UN HORARIO.
	private int parsearHora(String[] datos, int indice) {
		assert datos[indice].charAt(2) == ':';
		int result = 0;
		if(datos[indice].substring(3, 5).equals("30")) {
			//TODO System.err.println("Estoy redondeando la hora para: " + datos[0]);
			if(indice == 4)
				result++;
		}
		else
			assert datos[indice].substring(3, 5).equals("00");
		
		result += Integer.valueOf(datos[indice].substring(0, 2));
		return result;
	}
	
	public List<Solicitud> getSolicitudes() {
		return solicitudes;
	}
}
