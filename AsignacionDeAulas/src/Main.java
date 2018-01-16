import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static void main(String args[]){
		Histograma histograma = new Histograma();
		
//		Solicitud s1 = new Solicitud("a", "lunes", 8, 10);
//		Solicitud s2 = new Solicitud("b", "lunes", 9, 11);
//		Solicitud s3 = new Solicitud("c", "lunes", 8, 12);
//		
//		histograma.agregarSolicitud(s1);
//		histograma.agregarSolicitud(s2);
//		histograma.agregarSolicitud(s3);
//		
//		
		
		//reader llama al parser, y en el resultado del reader obtengo la lista.
		List<Solicitud> solicitudes = new ArrayList<>();
		CSVParser parser = new CSVParser(solicitudes);
		CSVReader reader = new CSVReader("pedidos.csv",parser);
		reader.read();
		for (Solicitud s: solicitudes) {
			histograma.agregarSolicitud(s);
		}
		CalculadorHistograma ch = new CalculadorHistograma(histograma);
		
		System.out.println(histograma.mostrarSolicitudes("Lunes"));
		System.out.println(ch.mostrarHoraMasSolicitada("Lunes"));
		System.out.println(histograma.mostrarSolicitudes("Martes"));
		System.out.println(ch.mostrarHoraMasSolicitada("Martes"));
		System.out.println(histograma.mostrarSolicitudes("Miércoles"));
		System.out.println(ch.mostrarHoraMasSolicitada("Miércoles"));
		System.out.println(histograma.mostrarSolicitudes("Jueves"));
		System.out.println(ch.mostrarHoraMasSolicitada("Jueves"));
		System.out.println(histograma.mostrarSolicitudes("Viernes"));
		System.out.println(ch.mostrarHoraMasSolicitada("Viernes"));
	}

}
