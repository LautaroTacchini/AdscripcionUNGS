import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static void main(String args[]){
		Histograma histograma = new Histograma();
		
		Solicitud s1 = new Solicitud("a", "lunes", 8, 10);
		Solicitud s2 = new Solicitud("b", "lunes", 9, 11);
		Solicitud s3 = new Solicitud("c", "lunes", 8, 12);
		
		histograma.agregarSolicitud(s1);
		histograma.agregarSolicitud(s2);
		histograma.agregarSolicitud(s3);
		
		CalculadorHistograma ch = new CalculadorHistograma(histograma);
		
		System.out.println(histograma.mostrarSolicitudes("lunes"));
		System.out.println(ch.mostrarHoraMasSolicitada("lunes"));
		
		//reader llama al parser, y en el resultado del reader obtengo la lista.
		List<Solicitud> solicitudes = new ArrayList<>();
		CSVParser parser = new CSVParser(solicitudes);
		CSVReader reader = new CSVReader("pedidos.csv",parser);
		reader.read();				
	}

}
