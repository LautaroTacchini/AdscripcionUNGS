package presentacion;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import negocio.CalculadorHistograma;
import negocio.Histograma;
import negocio.Solicitud;

public class Main {
	
	public static void main(String args[]){
		List<Solicitud> solicitudes = new ArrayList<>();
		
		Histograma histograma = new Histograma(solicitudes,15,30);
		CalculadorHistograma ch = new CalculadorHistograma(histograma);
	
		CSVParser parser = new CSVParser(solicitudes);
		CSVReader reader = new CSVReader("pedidos.csv",parser);
		try {
			reader.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		System.out.println(ch.imprimirSolicitudesSemanales());
//		System.out.println(ch.imprimirHrMasSolicitadaPorDia());	
		System.out.println(histograma.mostrarSolicitudesPorHorario("Lunes"));
	}

}
