import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static void main(String args[]){
		List<Solicitud> solicitudes = new ArrayList<>();
		CSVParser parser = new CSVParser(solicitudes);
		CSVReader reader = new CSVReader("pedidos.csv",parser);
		reader.read();
		Histograma histograma = new Histograma(solicitudes);
		
		CalculadorHistograma ch = new CalculadorHistograma(histograma);
		
//		System.out.println(ch.mostrarSolicitudesPorHora("Lunes"));
//		System.out.println(ch.mostrarHoraMasSolicitada("Lunes"));
//		System.out.println(ch.mostrarSolicitudesPorHora("Martes"));
//		System.out.println(ch.mostrarHoraMasSolicitada("Martes"));
//		System.out.println(ch.mostrarSolicitudesPorHora("Miércoles"));
//		System.out.println(ch.mostrarHoraMasSolicitada("Miércoles"));
//		System.out.println(ch.mostrarSolicitudesPorHora("Jueves"));
//		System.out.println(ch.mostrarHoraMasSolicitada("Jueves"));
//		System.out.println(ch.mostrarSolicitudesPorHora("Viernes"));
//		System.out.println(ch.mostrarHoraMasSolicitada("Viernes"));

		System.out.println(ch.mostrarHorasSemanalesMasSolicitada());
	}

}
