package presentacion;
import java.util.List;

import negocio.Horario;
import negocio.Solicitud;

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
		
		Horario hrIni = parsearHora(datos[3]);
		Horario hrFin = parsearHora(datos[4]);
		
		Solicitud s = new Solicitud(nombre,dia,hrIni,hrFin);
		solicitudes.add(s);
	}

	private Horario parsearHora(String datos) {
		assert datos.charAt(2) == ':';
		Horario horario = new Horario(Integer.valueOf(datos.substring(0,2)), Integer.valueOf(datos.substring(3,5)));
		return horario;
	}
	
}
