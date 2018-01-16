import java.util.List;

public class CSVParser {
	List<Solicitud> solicitudes;
	
	public CSVParser(List<Solicitud> solicitudes) {
		assert solicitudes != null;
		this.solicitudes = solicitudes;
	}
	
	public void parse(String entrada) {
		String[] datos = entrada.split(";");
		
		int columna = 0;
		
		String nombre = datos[0] + datos[1];
		String dia = datos[2];
		
		// TODO extraer, parsear la hora. 
		assert datos[3].charAt(3) == ':';
		assert datos[3].substring(4, 5).equals("00");
		System.out.println(datos[3]);
		int hrIni = Integer.valueOf(datos[3].substring(0, 2));
		System.out.println(hrIni);
		System.exit(0);
		int hrFin = 0;
		
		
	}
}
