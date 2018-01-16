import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static void main(String args[]){
		List<Solicitud> solicitudes = new ArrayList<>();
		
		Histograma histograma = new Histograma(solicitudes);
		CalculadorHistograma ch = new CalculadorHistograma(histograma);
	
		CSVParser parser = new CSVParser(solicitudes);
		CSVReader reader = new CSVReader("pedidos.csv",parser);
		reader.read();
		
		System.out.println(ch.imprimirSolicitudesSemanales());
		System.out.println(ch.imprimirHrMasSolicitadaPorDia());	
	}

}
